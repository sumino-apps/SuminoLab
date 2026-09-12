/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.feature.settings.state

import androidx.compose.runtime.Immutable
import com.sumino.core.config.AppConfig
import com.sumino.core.theme.ThemeMode

/**
 * UI State for the Settings screen.
 */
@Immutable
data class SettingsUiState(
    val currentTheme: ThemeMode = ThemeMode.SYSTEM,
    val appVersion: String = AppConfig.versionLabel,
    val applicationId: String = AppConfig.applicationId
)
