package com.sumino.xyz.data.local.database

import androidx.room.TypeConverter

/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 06-01-2026 at 1:54 PM
 */
object Converters {

    @TypeConverter
    fun fromBoolean(value : Boolean) : Int {
        return if (value) 1 else 0
    }

    @TypeConverter
    fun toBoolean(value : Int) : Boolean {
        return value != 0
    }
}