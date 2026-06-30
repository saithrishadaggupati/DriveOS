package com.driveos.dashboard.data.repository

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.driveos.dashboard.data.db.TelemetryDao
import com.driveos.dashboard.data.model.DTCCode
import com.driveos.dashboard.data.model.TelemetrySnapshot
import com.driveos.dashboard.data.model.VehicleState
import com.driveos.dashboard.service.CANBusService
import com.driveos.dashboard.service.CANFrame
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehicleRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val telemetryDao: TelemetryDao
) {
    private val repositoryScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _vehicleState = MutableStateFlow(VehicleState())
    val vehicleState: StateFlow<VehicleState> = _vehicleState

    private val _activeDTCs = MutableStateFlow(
        DTCCode.SIMULATED_CODES.take(2)
    )
    val activeDTCs: StateFlow<List<DTCCode>> = _activeDTCs

    fun clearDTCs() {
        _activeDTCs.value = emptyList()
    }

    // Trip tracking
    private var tripStartTime = System.currentTimeMillis()
    private var lastUpdateTime = System.currentTimeMillis()
    private var lastFuelLevel = -1f
    private var tripDistanceKm = 0f
    private var fuelConsumedL = 0f
    private var speedSum = 0f
    private var speedSampleCount = 0
    private var snapshotTick = 0

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = intent.getIntExtra(CANBusService.EXTRA_FRAME_ID, -1)
            val payload = intent.getFloatExtra(CANBusService.EXTRA_FRAME_PAYLOAD, 0f)
            val current = _vehicleState.value

            var updated = when (id) {
                CANFrame.ID_SPEED       -> current.copy(speedKph = payload)
                CANFrame.ID_RPM         -> current.copy(engineRpm = payload)
                CANFrame.ID_FUEL        -> current.copy(fuelLevelPercent = payload)
                CANFrame.ID_ENGINE_TEMP -> current.copy(coolantTempC = payload)
                CANFrame.ID_BATTERY     -> current.copy(battery = payload)
                CANFrame.ID_GEAR        -> current.copy(gear = payload.toInt())
                CANFrame.ID_LEFT_TURN   -> current.copy(leftTurn = payload == 1f)
                CANFrame.ID_RIGHT_TURN  -> current.copy(rightTurn = payload == 1f)
                CANFrame.ID_HEADLIGHTS  -> current.copy(headlights = payload == 1f)
                CANFrame.ID_SEATBELT    -> current.copy(seatbelt = payload == 1f)
                else -> current
            }

            if (id == CANFrame.ID_SPEED) {
                val now = System.currentTimeMillis()
                val deltaHours = (now - lastUpdateTime) / 3_600_000f
                tripDistanceKm += payload * deltaHours
                lastUpdateTime = now

                speedSum += payload
                speedSampleCount++
            }

            if (id == CANFrame.ID_FUEL) {
                if (lastFuelLevel < 0f) lastFuelLevel = payload
                val dropPercent = (lastFuelLevel - payload).coerceAtLeast(0f)
                // Assume a 50L tank for simulation purposes
                fuelConsumedL += dropPercent / 100f * 50f
                lastFuelLevel = payload
            }

            val avgSpeed = if (speedSampleCount > 0) speedSum / speedSampleCount else 0f
            val durationSeconds = (System.currentTimeMillis() - tripStartTime) / 1000L
            val economy = if (tripDistanceKm > 0.05f) (fuelConsumedL / tripDistanceKm) * 100f else 0f

            updated = updated.copy(
                tripDistanceKm = tripDistanceKm,
                avgSpeedKph = avgSpeed,
                fuelConsumedL = fuelConsumedL,
                fuelEconomyL100km = economy,
                tripDurationSeconds = durationSeconds,
                odometerKm = 12450f + tripDistanceKm
            )

            _vehicleState.value = updated

            // Persist a snapshot roughly every 5 seconds (100 ticks at 20Hz)
            snapshotTick++
            if (snapshotTick >= 100) {
                snapshotTick = 0
                saveSnapshot(updated)
            }
        }
    }

    private fun saveSnapshot(state: VehicleState) {
        repositoryScope.launch {
            telemetryDao.insertSnapshot(
                TelemetrySnapshot(
                    speed = state.speedKph,
                    rpm = state.engineRpm,
                    fuel = state.fuelLevelPercent,
                    engineTemp = state.coolantTempC,
                    battery = state.battery,
                    gear = state.gear,
                    driveMode = state.driveMode,
                    tripDistanceKm = state.tripDistanceKm
                )
            )
        }
    }

    fun register() {
        val filter = IntentFilter(CANBusService.ACTION_CAN_FRAME)
        context.registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED)
    }

    fun unregister() {
        try { context.unregisterReceiver(receiver) } catch (e: Exception) { }
    }
}