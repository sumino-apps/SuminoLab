package com.sumino.xyz.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * This object handles the SharedPreferences operations.
 * It provides methods to save, retrieve, and delete preferences.
 * This class demonstrates the use of SharedPreferences for storing user preferences.
 *
 */
@Singleton
class SharedPref @Inject constructor(
    private val preferences: SharedPreferences,
    @ApplicationContext private val context: Context
) {


    var isFirstTimeLaunch by preference<Boolean>("firstTime", true)

    var isMobileAdsInitialized by preference<Boolean>("is_mobile_ads_initialized", false)


    /**
     * Registers a listener to be notified when a shared preference is changed.
     *
     * @param listener The callback that will run when a shared preference is changed.
     */
    fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        Timber.Forest.tag("SharedPref").d("registerOnSharedPref")
        preferences.registerOnSharedPreferenceChangeListener(listener)
    }

    /**
     * Unregisters a previously registered listener.
     *
     * @param listener The callback to be unregistered.
     */
    fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        Timber.Forest.tag("SharedPref").d("unregisterOnSharedPref")
        preferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    /**
     * Clear all preferences
     */
    fun clearAll() {
        preferences.edit { clear() }
        Timber.Forest.tag("SharedPref").d("All preferences cleared")
    }

    /**
     * Remove specific preference
     */
    fun remove(key: String) {
        preferences.edit { remove(key) }
        Timber.Forest.tag("SharedPref").d("Removed preference: $key")
    }

    /**
     * Check if preference exists
     */
    fun contains(key: String): Boolean {
        return preferences.contains(key)
    }

    /**
     * Get all preferences
     */
    fun getAll(): Map<String, *> {
        return preferences.all
    }

    /**
     * Generic preference delegate for all types (nullable and non-nullable).
     * @param name The key for the preference.
     * @param defaultValue The default value for the preference (nullable).
     */
    private inline fun <reified T> preference(
        name: String,
        defaultValue: T
    ): ReadWriteProperty<SharedPref, T> {
        return object : ReadWriteProperty<SharedPref, T> {

            override fun getValue(thisRef: SharedPref, property: KProperty<*>): T {
                return when (T::class) {
                    Boolean::class -> preferences.getBoolean(name, defaultValue as Boolean) as T
                    Int::class -> preferences.getInt(name, defaultValue as Int) as T
                    Long::class -> preferences.getLong(name, defaultValue as Long) as T
                    Float::class -> preferences.getFloat(name, defaultValue as Float) as T
                    String::class -> {
                        val value = preferences.getString(name, null)
                        @Suppress("UNCHECKED_CAST")
                        (value ?: defaultValue) as T
                    }
                    else -> {
                        Timber.Forest.tag("SharedPref").e("Unsupported type: ${T::class.java}")
                        defaultValue
                    }
                }
            }

            override fun setValue(thisRef: SharedPref, property: KProperty<*>, value: T) {
                preferences.edit {
                    when (value) {
                        is Boolean -> putBoolean(name, value)
                        is Int -> putInt(name, value)
                        is Long -> putLong(name, value)
                        is Float -> putFloat(name, value)
                        is String -> putString(name, value)
                        null -> remove(name) // Handle null for nullable types
                        else -> {
                            Timber.Forest.tag("SharedPref").e("Unsupported type: ${T::class.java}")
                        }
                    }
                }
            }
        }
    }


}