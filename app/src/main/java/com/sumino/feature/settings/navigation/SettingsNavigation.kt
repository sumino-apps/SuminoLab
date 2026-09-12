/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sumino.feature.settings.SettingsRoute
import kotlinx.serialization.Serializable

/**
 * Type-safe destination route object for Settings.
 */
@Serializable
object SettingsRoute

/**
 * Registers the Settings screen in the navigation graph.
 */
fun NavGraphBuilder.settingsScreen(
    onNavigateBack: () -> Unit
) {
    composable<SettingsRoute> {
        SettingsRoute(onNavigateBack = onNavigateBack)
    }
}

/**
 * Navigates to the Settings screen.
 */
fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    navigate(SettingsRoute, navOptions)
}
