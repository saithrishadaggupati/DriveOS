package com.driveos.dashboard.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "telemetry_snapshots")
data class TelemetrySnapshot(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val speed: Float,
    val rpm: Float,
    val fuel: Float,
    val engineTemp: Float,
    val battery: Float,
    val gear: Int,
    val driveMode: Int,
    val tripDistanceKm: Float = 0f
)