/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.di

import android.content.Context
import androidx.room.Room
import com.sumino.core.config.AppConfig
import com.sumino.data.local.database.AppDatabase
import com.sumino.data.local.database.dao.AppDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing Room database instances and DAOs.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        AppConfig.roomDatabaseName
    ).fallbackToDestructiveMigration(dropAllTables = true)
        .build()

    @Provides
    fun provideAppDao(database: AppDatabase): AppDao = database.appDao()
}
