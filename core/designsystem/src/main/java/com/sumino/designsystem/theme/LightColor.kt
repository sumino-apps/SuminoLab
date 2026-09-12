/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// ── Light color tokens (Harmonized with #0B57D0) ──────────────────────────────

// Primary — Google signature blue (#0B57D0)
val primaryLight = Color(0xFF0B57D0)
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFFD3E3FD)
val onPrimaryContainerLight = Color(0xFF041E49)

// Secondary — harmonized slate blue
val secondaryLight = Color(0xFF4C5E7D)
val onSecondaryLight = Color(0xFFFFFFFF)
val secondaryContainerLight = Color(0xFFD5E3FF)
val onSecondaryContainerLight = Color(0xFF041C36)

// Tertiary — harmonized soft plum / violet
val tertiaryLight = Color(0xFF6B5778)
val onTertiaryLight = Color(0xFFFFFFFF)
val tertiaryContainerLight = Color(0xFFF3DAFF)
val onTertiaryContainerLight = Color(0xFF251432)

// Error — Material 3 standard red
val errorLight = Color(0xFFBA1A1A)
val onErrorLight = Color(0xFFFFFFFF)
val errorContainerLight = Color(0xFFFFDAD6)
val onErrorContainerLight = Color(0xFF410002)

// Background & Surface
val backgroundLight = Color(0xFFF8F9FF)
val onBackgroundLight = Color(0xFF191C20)
val surfaceLight = Color(0xFFF8F9FF)
val onSurfaceLight = Color(0xFF191C20)
val surfaceVariantLight = Color(0xFFDFE2EC)
val onSurfaceVariantLight = Color(0xFF43474E)

// Outlines & Scrim
val outlineLight = Color(0xFF73777F)
val outlineVariantLight = Color(0xFFC3C6CF)
val scrimLight = Color(0xFF000000)

// Inverse roles
val inverseSurfaceLight = Color(0xFF2E3035)
val inverseOnSurfaceLight = Color(0xFFEFF0F7)
val inversePrimaryLight = Color(0xFFA8C7FA)

// Material 3 Fixed roles
val primaryFixedLight = Color(0xFFD3E3FD)
val onPrimaryFixedLight = Color(0xFF041E49)
val primaryFixedDimLight = Color(0xFFA8C7FA)
val onPrimaryFixedVariantLight = Color(0xFF0842A0)

val secondaryFixedLight = Color(0xFFD5E3FF)
val onSecondaryFixedLight = Color(0xFF041C36)
val secondaryFixedDimLight = Color(0xFFB3C8EB)
val onSecondaryFixedVariantLight = Color(0xFF344664)

val tertiaryFixedLight = Color(0xFFF3DAFF)
val onTertiaryFixedLight = Color(0xFF251432)
val tertiaryFixedDimLight = Color(0xFFD7BEE4)
val onTertiaryFixedVariantLight = Color(0xFF523F5E)

// Surface containers
val surfaceDimLight = Color(0xFFD8DAE2)
val surfaceBrightLight = Color(0xFFF8F9FF)
val surfaceContainerLowestLight = Color(0xFFFFFFFF)
val surfaceContainerLowLight = Color(0xFFF2F3FB)
val surfaceContainerLight = Color(0xFFECEEF5)
val surfaceContainerHighLight = Color(0xFFE6E8F0)
val surfaceContainerHighestLight = Color(0xFFE1E2EA)

/**
 * Material 3 light color scheme for SuminoLab (harmonized with #0B57D0).
 */
val LightColorScheme: ColorScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    primaryFixed = primaryFixedLight,
    onPrimaryFixed = onPrimaryFixedLight,
    primaryFixedDim = primaryFixedDimLight,
    onPrimaryFixedVariant = onPrimaryFixedVariantLight,
    secondaryFixed = secondaryFixedLight,
    onSecondaryFixed = onSecondaryFixedLight,
    secondaryFixedDim = secondaryFixedDimLight,
    onSecondaryFixedVariant = onSecondaryFixedVariantLight,
    tertiaryFixed = tertiaryFixedLight,
    onTertiaryFixed = onTertiaryFixedLight,
    tertiaryFixedDim = tertiaryFixedDimLight,
    onTertiaryFixedVariant = onTertiaryFixedVariantLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)
