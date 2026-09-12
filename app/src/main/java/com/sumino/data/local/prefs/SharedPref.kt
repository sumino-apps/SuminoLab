/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.sumino.core.theme.ThemeMode
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Singleton wrapper around [SharedPreferences] providing type-safe property access,
 * Gson-backed object persistence, and change listener management.
 */
@Singleton
class SharedPref @Inject constructor(
    private val preferences: SharedPreferences,
    private val gson: Gson,
    @ApplicationContext private val context: Context
) {

    companion object {
        const val PREF_FILE_NAME = "sumino_app_preferences"
    }

    /**
     * Registers a listener for SharedPreferences changes.
     */
    fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        preferences.registerOnSharedPreferenceChangeListener(listener)
    }

    /**
     * Unregisters a previously registered SharedPreferences listener.
     */
    fun unregisterListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        preferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    // --- Onboarding & Consent ---

    var isFirstTimeLaunch: Boolean
        get() = preferences.getBoolean(PrefKeys.IS_FIRST_TIME_LAUNCH, true)
        set(value) {
            preferences.edit { putBoolean(PrefKeys.IS_FIRST_TIME_LAUNCH, value) }
        }

    var firstLaunchTime: Long
        get() = preferences.getLong(PrefKeys.FIRST_LAUNCH_TIME, 0L)
        set(value) {
            preferences.edit { putLong(PrefKeys.FIRST_LAUNCH_TIME, value) }
        }

    var acceptPrivacyPolicy: Boolean
        get() = preferences.getBoolean(PrefKeys.ACCEPT_PRIVACY_POLICY, false)
        set(value) {
            preferences.edit { putBoolean(PrefKeys.ACCEPT_PRIVACY_POLICY, value) }
        }

    // --- Theming ---

    var themeMode: ThemeMode
        get() {
            val raw = preferences.getString(PrefKeys.THEME_MODE, null) ?: return ThemeMode.SYSTEM
            return runCatching { ThemeMode.valueOf(raw) }.getOrDefault(ThemeMode.SYSTEM)
        }
        set(value) {
            preferences.edit { putString(PrefKeys.THEME_MODE, value.name) }
        }

    // --- Review & Ratings ---

    var hasUserReviewed: Boolean
        get() = preferences.getBoolean(PrefKeys.HAS_USER_REVIEWED, false)
        set(value) {
            preferences.edit { putBoolean(PrefKeys.HAS_USER_REVIEWED, value) }
        }

    var lastReviewPromptTime: Long
        get() = preferences.getLong(PrefKeys.LAST_REVIEW_PROMPT_TIME, 0L)
        set(value) {
            preferences.edit { putLong(PrefKeys.LAST_REVIEW_PROMPT_TIME, value) }
        }

    // --- Generic Object Helpers (Gson) ---

    fun <T> putObject(key: String, obj: T?) {
        if (obj == null) {
            preferences.edit { remove(key) }
            return
        }
        runCatching {
            val json = gson.toJson(obj)
            preferences.edit { putString(key, json) }
        }.onFailure {
            Timber.e(it, "Failed to serialize object for key: $key")
        }
    }

    inline fun <reified T> getObject(key: String, defaultValue: T? = null): T? {
        val json = preferences.getString(key, null) ?: return defaultValue
        return runCatching {
            gson.fromJson(json, T::class.java)
        }.getOrElse {
            Timber.e(it, "Failed to deserialize object for key: $key")
            defaultValue
        }
    }

    fun clearAll() {
        preferences.edit { clear() }
    }
}
