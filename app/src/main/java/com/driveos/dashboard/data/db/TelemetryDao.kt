package com.driveos.dashboard.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.driveos.dashboard.data.model.DTCCode
import com.driveos.dashboard.data.model.TelemetrySnapshot

@Dao
interface TelemetryDao {

    // Telemetry
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSnapshot(snapshot: TelemetrySnapshot)

    @Query("SELECT * FROM telemetry_snapshots ORDER BY timestamp DESC LIMIT 100")
    fun getRecentSnapshots(): LiveData<List<TelemetrySnapshot>>

    @Query("SELECT AVG(speed) FROM telemetry_snapshots WHERE timestamp > :since")
    suspend fun getAverageSpeed(since: Long): Float

    @Query("SELECT MAX(speed) FROM telemetry_snapshots WHERE timestamp > :since")
    suspend fun getMaxSpeed(since: Long): Float

    @Query("SELECT SUM(tripDistanceKm) FROM telemetry_snapshots WHERE timestamp > :since")
    suspend fun getTripDistance(since: Long): Float

    @Query("DELETE FROM telemetry_snapshots WHERE timestamp < :before")
    suspend fun deleteOldSnapshots(before: Long)

    // DTC Codes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDTCCode(code: DTCCode)

    @Query("SELECT * FROM dtc_codes WHERE isActive = 1 ORDER BY timestamp DESC")
    fun getActiveDTCCodes(): LiveData<List<DTCCode>>

    @Query("UPDATE dtc_codes SET isActive = 0 WHERE code = :code")
    suspend fun clearDTCCode(code: String)

    @Query("DELETE FROM dtc_codes")
    suspend fun clearAllDTCCodes()
}