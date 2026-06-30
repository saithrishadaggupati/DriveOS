package com.driveos.dashboard.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*
import kotlin.math.*
import kotlin.random.Random

class CANBusService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // Simulated vehicle state
    private var speed = 0f
    private var rpm = 800f
    private var fuel = 75f
    private var engineTemp = 35f
    private var battery = 12.6f
    private var gear = 1
    private var driveMode = 0 // 0=Normal, 1=Sport, 2=Eco
    private var headlights = false
    private var leftTurn = false
    private var rightTurn = false
    private var doorFL = false
    private var doorFR = false
    private var seatbelt = false

    private var accelerating = true
    private var tick = 0

    companion object {
        const val ACTION_CAN_FRAME = "com.driveos.CAN_FRAME"
        const val EXTRA_FRAME_ID = "frame_id"
        const val EXTRA_FRAME_PAYLOAD = "frame_payload"
        const val CHANNEL_ID = "DriveOSServiceChannel"
        const val NOTIF_ID = 1
    }

    override fun onCreate() {
        super.onCreate()
        startForeground(NOTIF_ID, buildNotification())
        startCANSimulation()
    }

    private fun startCANSimulation() {
        serviceScope.launch {
            while (isActive) {
                tick++

                // Simulate realistic speed curve
                if (accelerating) {
                    speed = (speed + Random.nextFloat() * 2.5f).coerceAtMost(180f)
                    if (speed >= 180f) accelerating = false
                } else {
                    speed = (speed - Random.nextFloat() * 2f).coerceAtLeast(0f)
                    if (speed <= 0f) accelerating = true
                }

                // RPM follows speed with engine behavior
                rpm = (800f + speed * 38f + Random.nextFloat() * 200f)
                    .coerceIn(800f, 8000f)

                // Gear shifts based on speed
                gear = when {
                    speed < 20f  -> 1
                    speed < 40f  -> 2
                    speed < 70f  -> 3
                    speed < 110f -> 4
                    speed < 150f -> 5
                    else         -> 6
                }

                // Fuel drains slowly
                if (tick % 50 == 0) fuel = (fuel - 0.1f).coerceAtLeast(0f)

                // Engine warms up
                engineTemp = (engineTemp + 0.05f).coerceAtMost(95f)

                // Battery slight fluctuation
                battery = 12.4f + Random.nextFloat() * 0.4f

                // Turn signals toggle every 2 seconds
                if (tick % 40 == 0) leftTurn = !leftTurn
                if (tick % 60 == 0) rightTurn = !rightTurn

                // Headlights on after tick 100
                headlights = tick > 100

                // Broadcast all frames
                broadcastFrame(CANFrame.ID_SPEED, speed)
                broadcastFrame(CANFrame.ID_RPM, rpm)
                broadcastFrame(CANFrame.ID_FUEL, fuel)
                broadcastFrame(CANFrame.ID_ENGINE_TEMP, engineTemp)
                broadcastFrame(CANFrame.ID_BATTERY, battery)
                broadcastFrame(CANFrame.ID_GEAR, gear.toFloat())
                broadcastFrame(CANFrame.ID_LEFT_TURN, if (leftTurn) 1f else 0f)
                broadcastFrame(CANFrame.ID_RIGHT_TURN, if (rightTurn) 1f else 0f)
                broadcastFrame(CANFrame.ID_HEADLIGHTS, if (headlights) 1f else 0f)
                broadcastFrame(CANFrame.ID_SEATBELT, if (seatbelt) 1f else 0f)

                delay(50L) // 20 frames per second — realistic CAN bus rate
            }
        }
    }

    private fun broadcastFrame(id: Int, payload: Float) {
        val intent = Intent(ACTION_CAN_FRAME).apply {
            putExtra(EXTRA_FRAME_ID, id)
            putExtra(EXTRA_FRAME_PAYLOAD, payload)
            setPackage(packageName)
        }
        sendBroadcast(intent)
    }

    private fun buildNotification(): Notification {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "DriveOS Vehicle Service",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("DriveOS Running")
            .setContentText("Vehicle telemetry active")
            .setSmallIcon(android.R.drawable.ic_menu_compass)
            .build()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}