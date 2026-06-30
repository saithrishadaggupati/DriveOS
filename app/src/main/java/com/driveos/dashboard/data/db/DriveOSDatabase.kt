package com.driveos.dashboard.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import android.content.Context
import com.driveos.dashboard.data.model.DTCCode
import com.driveos.dashboard.data.model.TelemetrySnapshot

@Database(
    entities = [TelemetrySnapshot::class, DTCCode::class],
    version = 2,
    exportSchema = false
)
abstract class DriveOSDatabase : RoomDatabase() {

    abstract fun telemetryDao(): TelemetryDao

    companion object {
        @Volatile
        private var INSTANCE: DriveOSDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Added tripDistanceKm column in v2
                database.execSQL(
                    "ALTER TABLE telemetry_snapshots ADD COLUMN tripDistanceKm REAL NOT NULL DEFAULT 0"
                )
            }
        }

        fun getInstance(context: Context): DriveOSDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    DriveOSDatabase::class.java,
                    "driveos.db"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}