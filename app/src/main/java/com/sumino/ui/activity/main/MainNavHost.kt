/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.ui.activity.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sumino.designsystem.theme.LocalDarkTheme
import com.sumino.designsystem.theme.SetSystemBarIcons
import com.sumino.feature.home.navigation.HomeRoute
import com.sumino.feature.home.navigation.homeScreen
import com.sumino.feature.settings.navigation.navigateToSettings
import com.sumino.feature.settings.navigation.settingsScreen

/**
 * Root navigation graph for the application.
 *
 * Hosts all in-app destinations, applies theme-aware system bar icon colors,
 * and coordinates cross-feature navigation edges.
 */
@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val isAppDark = LocalDarkTheme.current
    SetSystemBarIcons(darkIcons = !isAppDark)

    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier
    ) {
        homeScreen(
            onNavigateToSettings = {
                navController.navigateToSettings()
            }
        )

        settingsScreen(
            onNavigateBack = {
                navController.navigateUp()
            }
        )
    }
}
