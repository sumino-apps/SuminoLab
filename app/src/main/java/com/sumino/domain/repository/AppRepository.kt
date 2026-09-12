/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.domain.repository

import com.sumino.core.common.Outcome
import com.sumino.data.local.datastore.model.DataStorePrefs
import com.sumino.domain.model.AppModel
import kotlinx.coroutines.flow.Flow

/**
 * Universal repository contract defining the domain layer's data requirements.
 *
 * Implementations live in the `data` layer ([com.sumino.data.repository.AppRepositoryImpl])
 * and are bound via [com.sumino.di.BindsModule].
 */
interface AppRepository {

    /**
     * Emits the complete list of application items, ordered from newest to oldest.
     */
    fun getItems(): Flow<List<AppModel>>

    /**
     * Emits a single item identified by [id], or null if not found.
     */
    fun getItemById(id: Long): Flow<AppModel?>

    /**
     * Emits only items marked as favorite.
     */
    fun getFavoriteItems(): Flow<List<AppModel>>

    /**
     * Inserts or updates an item in persistent storage.
     */
    suspend fun saveItem(item: AppModel): Outcome<Long>

    /**
     * Deletes an item by [id].
     */
    suspend fun deleteItem(id: Long): Outcome<Unit>

    /**
     * Real-time stream of user preferences.
     */
    fun getPreferences(): Flow<DataStorePrefs>

    /**
     * Updates user preferences atomically.
     */
    suspend fun updatePreferences(update: DataStorePrefs.() -> DataStorePrefs)
}
