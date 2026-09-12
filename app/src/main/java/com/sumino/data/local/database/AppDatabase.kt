/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sumino.data.local.database.dao.AppDao
import com.sumino.data.local.database.entity.AppEntity

/**
 * The application's Room database.
 *
 * Instantiated as a Singleton via [com.sumino.di.DatabaseModule], using the database
 * name defined in [com.sumino.core.config.AppConfig.roomDatabaseName].
 *
 * Notice: For production apps undergoing migrations, set `exportSchema = true` and
 * configure `room.schemaLocation` in build.gradle.kts.
 */
@Database(
    entities = [
        AppEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao
}
