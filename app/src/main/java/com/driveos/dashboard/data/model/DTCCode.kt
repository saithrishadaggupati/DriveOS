package com.driveos.dashboard.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dtc_codes")
data class DTCCode(
    @PrimaryKey
    val code: String,
    val description: String,
    val severity: Severity,
    val timestamp: Long = System.currentTimeMillis(),
    val isActive: Boolean = true
) {
    enum class Severity {
        LOW, MEDIUM, CRITICAL
    }

    companion object {
        // Real DTC codes used in production ECUs
        val SIMULATED_CODES = listOf(
            DTCCode("P0301", "Cylinder 1 Misfire Detected", Severity.CRITICAL),
            DTCCode("P0420", "Catalyst System Efficiency Below Threshold", Severity.MEDIUM),
            DTCCode("P0171", "System Too Lean (Bank 1)", Severity.MEDIUM),
            DTCCode("P0128", "Coolant Temp Below Thermostat Regulating Temp", Severity.LOW),
            DTCCode("P0562", "System Voltage Low", Severity.CRITICAL),
            DTCCode("P0011", "Camshaft Position Timing Over-Advanced", Severity.MEDIUM),
            DTCCode("P0455", "Evaporative Emission System Leak Detected", Severity.LOW),
            DTCCode("P0700", "Transmission Control System Malfunction", Severity.CRITICAL)
        )
    }
}