/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.feature.home.state

import androidx.compose.runtime.Immutable

/**
 * UI State for the Home screen.
 * Marked as [@Immutable] to optimize Compose recompositions.
 */
@Immutable
data class HomeUiState(
    val isOnline: Boolean = true,
    val title: String = "SuminoLab Starter Template",
    val subtitle: String = "Universal Android base platform with Clean Architecture"
)
