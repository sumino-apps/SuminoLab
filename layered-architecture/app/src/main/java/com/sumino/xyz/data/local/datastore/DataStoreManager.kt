package com.sumino.xyz.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.sumino.xyz.data.local.models.AppPreferencesData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages the application's preferences using DataStore.
 *
 * @param context The application context.
 * @param appPreferencesSerializer The serializer for the preferences data.
 */
@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val context : Context,
    appPreferencesSerializer : AppPreferencesSerializer
) {

    /**
     * The DataStore instance for storing and retrieving preferences.
     */
    private val dataStore : DataStore<AppPreferencesData> = DataStoreFactory.create(
        produceFile = { context.dataStoreFile("app_preferences.json") },
        serializer = appPreferencesSerializer
    )

    /**
     * A flow of the preferences data, with error handling and default value emission.
     *
     * @throws androidx.datastore.core.IOException If an IO exception occurs while reading the preferences data.
     * @throws Exception If any other exception occurs.
     */
    @OptIn(DelicateCoroutinesApi::class)
    val preferences = dataStore.data
        .catch { exception ->
            Timber.e("Error reading preferences: ${exception.message}")
            emit(AppPreferencesData()) // Default values emit error case me
        }
        .stateIn(
            scope = GlobalScope,
            started = SharingStarted.WhileSubscribed(15000L),
            initialValue = AppPreferencesData()
        )

    /**
     * Retrieves a preference value using a selector function.
     *
     * @param selector A function that selects a value from the preferences data.
     * @return The selected preference value. If an exception occurs, the default value of the preference data is returned.
     */
    suspend fun <T> getPreference(selector : (AppPreferencesData) -> T) : T {
        return try {
            selector(preferences.first())
        } catch (e : Exception) {
            Timber.tag("DataStore").e("getPreference: ${e.message}")
            selector(AppPreferencesData())
        }
    }


    /**
     * Updates the preferences data using an update function.
     *
     * @param update A function that updates the preferences data.
     */
    suspend fun updatePreference(update : AppPreferencesData.() -> AppPreferencesData) {
        try {
            dataStore.updateData { currentPreferences ->

                Timber.tag("DataStore").d("Before Update: ${currentPreferences.dialogPreviouslyShown}")

                val updatedPreferences = currentPreferences.update()

                // Log after update
                Timber.tag("DataStore").d("After Update: ${updatedPreferences.dialogPreviouslyShown}")

                updatedPreferences
            }
        } catch (e : Exception) {
            Timber.tag("DataStore").e("updatePreference: error ${e.message}")
        }
    }


}