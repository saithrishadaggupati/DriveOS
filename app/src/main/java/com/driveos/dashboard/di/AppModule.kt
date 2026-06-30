package com.driveos.dashboard.di

import android.content.Context
import androidx.room.Room
import com.driveos.dashboard.data.db.DriveOSDatabase
import com.driveos.dashboard.data.db.TelemetryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DriveOSDatabase {
        return Room.databaseBuilder(
            context,
            DriveOSDatabase::class.java,
            "driveos.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTelemetryDao(db: DriveOSDatabase): TelemetryDao = db.telemetryDao()
}
