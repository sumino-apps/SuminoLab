/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.data.local.datastore.model

import com.sumino.core.theme.ThemeMode
import kotlinx.serialization.Serializable

/**
 * DataStore preferences data model.
 *
 * Holds reactive, structured preferences stored asynchronously via [androidx.datastore.core.DataStore].
 * Add any screen or user configuration fields here.
 */
@Serializable
data class DataStorePrefs(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val useDynamicColor: Boolean = true,
    val isHapticFeedbackEnabled: Boolean = true,
    val isSoundEffectsEnabled: Boolean = true,
    val isAutoSyncEnabled: Boolean = false,
    val dialogPreviouslyShown: Boolean = false,
    val customNotes: String = ""
)
