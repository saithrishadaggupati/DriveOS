package com.driveos.dashboard.service

data class CANFrame(
    val id: Int,
    val payload: Float,
    val timestamp: Long = System.currentTimeMillis()
) {
    companion object {
        // Standard CAN IDs — mirrors real automotive spec
        const val ID_SPEED       = 0x0C4
        const val ID_RPM         = 0x0C0
        const val ID_FUEL        = 0x2F4
        const val ID_ENGINE_TEMP = 0x130
        const val ID_BATTERY     = 0x1DB
        const val ID_GEAR        = 0x3B3
        const val ID_LEFT_TURN   = 0x3BC
        const val ID_RIGHT_TURN  = 0x3BD
        const val ID_DOOR_FL     = 0x3D5
        const val ID_DOOR_FR     = 0x3D6
        const val ID_SEATBELT    = 0x3C0
        const val ID_HEADLIGHTS  = 0x3E0
        const val ID_DRIVE_MODE  = 0x3F0
    }
}