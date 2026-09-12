/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.sumino.feature.home.HomeRoute
import kotlinx.serialization.Serializable

/**
 * Type-safe destination route object for Home.
 */
@Serializable
object HomeRoute

/**
 * Registers the Home destination in the navigation graph.
 */
fun NavGraphBuilder.homeScreen(
    onNavigateToSettings: () -> Unit
) {
    composable<HomeRoute> {
        HomeRoute(onNavigateToSettings = onNavigateToSettings)
    }
}

/**
 * Navigates to the Home destination.
 */
fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(HomeRoute, navOptions)
}
