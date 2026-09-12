/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.sumino.data.local.datastore.model.DataStorePrefs
import com.sumino.di.ApplicationScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages reactive application preferences using typed [DataStore].
 *
 * Exposes a reactive [preferencesFlow] stream and asynchronous getters/updaters.
 */
@Singleton
class ProtoDataStorePrefs @Inject constructor(
    @ApplicationContext private val context: Context,
    dataStorePrefsSerializer: DataStorePrefsSerializer,
    @ApplicationScope private val appScope: CoroutineScope
) {

    companion object {
        const val DATASTORE_FILE = "app_preferences.pb"
    }

    private val dataStore: DataStore<DataStorePrefs> = DataStoreFactory.create(
        serializer = dataStorePrefsSerializer,
        scope = appScope,
        produceFile = { context.dataStoreFile(DATASTORE_FILE) }
    )

    /**
     * Flow of [DataStorePrefs] emitting current state and subsequent updates.
     */
    val preferencesFlow: Flow<DataStorePrefs> = dataStore.data

    /**
     * Retrieves a snapshot of a specific preference property.
     */
    suspend fun <T> getPreference(selector: (DataStorePrefs) -> T): T {
        return preferencesFlow.first().let(selector)
    }

    /**
     * Atomically updates preferences using a transformation lambda.
     */
    suspend fun updatePreference(update: DataStorePrefs.() -> DataStorePrefs) {
        dataStore.updateData { current ->
            current.update()
        }
    }
}
