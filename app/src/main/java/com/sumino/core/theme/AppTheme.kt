/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.sumino.designsystem.theme.SuminoLabTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Supported theme modes for the app.
 */
enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}

/**
 * In-memory state holder for the active theme mode.
 * Initialized during [android.app.Application.onCreate] to prevent cold-start theme flicker.
 */
object ThemeManager {
    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    fun init(initialMode: ThemeMode) {
        _themeMode.value = initialMode
    }

    fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
    }
}

/**
 * Top-level application theme composable.
 * Bridges reactive [ThemeManager] state to [SuminoLabTheme].
 */
@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    val themeMode by ThemeManager.themeMode.collectAsState()
    val isDark = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }
    SuminoLabTheme(darkTheme = isDark) {
        content()
    }
}
