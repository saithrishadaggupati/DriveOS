package com.driveos.dashboard.data.model

data class VehicleState(
    val speedKph: Float = 0f,
    val engineRpm: Float = 800f,
    val fuelLevelPercent: Float = 75f,
    val coolantTempC: Float = 35f,
    val battery: Float = 12.6f,
    val gear: Int = 1,
    val leftTurn: Boolean = false,
    val rightTurn: Boolean = false,
    val headlights: Boolean = false,
    val seatbelt: Boolean = false,
    val doorFL: Boolean = false,
    val doorFR: Boolean = false,
    val driveMode: Int = 0,
    val cabinTempC: Float = 22f,
    val targetTempC: Float = 22f,
    val fanSpeed: Int = 2,
    val acOn: Boolean = true,
    val tripDistanceKm: Float = 0f,
    val avgSpeedKph: Float = 0f,
    val fuelConsumedL: Float = 0f,
    val fuelEconomyL100km: Float = 0f,
    val tripDurationSeconds: Long = 0L,
    val odometerKm: Float = 12450f
)
