/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

// ── Dark color tokens (Style D: Warm Charcoal + Amber Gold) ───────────────────

// Primary — Amber Gold / Sunset Tone 80 (warm, highly legible on dark)
val primaryDark = Color(0xFFFFB74D)
val onPrimaryDark = Color(0xFF4A2800)
val primaryContainerDark = Color(0xFF6B3B00)
val onPrimaryContainerDark = Color(0xFFFFDDB3)

// Secondary — Warm honey / bronze Tone 80
val secondaryDark = Color(0xFFDEC09B)
val onSecondaryDark = Color(0xFF3E2C11)
val secondaryContainerDark = Color(0xFF564225)
val onSecondaryContainerDark = Color(0xFFFBDDB6)

// Tertiary — Warm terracotta / rose Tone 80
val tertiaryDark = Color(0xFFE5BDBA)
val onTertiaryDark = Color(0xFF442928)
val tertiaryContainerDark = Color(0xFF5D3F3D)
val onTertiaryContainerDark = Color(0xFFFFDAD8)

// Error — Material 3 standard dark error
val errorDark = Color(0xFFFFB4AB)
val onErrorDark = Color(0xFF690005)
val errorContainerDark = Color(0xFF93000A)
val onErrorContainerDark = Color(0xFFFFDAD6)

// Background & Surface — Deep warm charcoal & espresso
val backgroundDark = Color(0xFF141210)
val onBackgroundDark = Color(0xFFEDE0D9)
val surfaceDark = Color(0xFF141210)
val onSurfaceDark = Color(0xFFEDE0D9)
val surfaceVariantDark = Color(0xFF50453D)
val onSurfaceVariantDark = Color(0xFFD4C4B8)

// Outlines & Scrim
val outlineDark = Color(0xFF9D8E84)
val outlineVariantDark = Color(0xFF50453D)
val scrimDark = Color(0xFF000000)

// Inverse roles
val inverseSurfaceDark = Color(0xFFEDE0D9)
val inverseOnSurfaceDark = Color(0xFF332F2C)
val inversePrimaryDark = Color(0xFF8C5000)

// Material 3 Fixed roles
val primaryFixedDark = Color(0xFFFFDDB3)
val onPrimaryFixedDark = Color(0xFF2C1600)
val primaryFixedDimDark = Color(0xFFFFB74D)
val onPrimaryFixedVariantDark = Color(0xFF6B3B00)

val secondaryFixedDark = Color(0xFFFBDDB6)
val onSecondaryFixedDark = Color(0xFF271702)
val secondaryFixedDimDark = Color(0xFFDEC09B)
val onSecondaryFixedVariantDark = Color(0xFF564225)

val tertiaryFixedDark = Color(0xFFFFDAD8)
val onTertiaryFixedDark = Color(0xFF2C1514)
val tertiaryFixedDimDark = Color(0xFFE5BDBA)
val onTertiaryFixedVariantDark = Color(0xFF5D3F3D)

// Surface containers (Warm charcoal tonal elevation)
val surfaceDimDark = Color(0xFF141210)
val surfaceBrightDark = Color(0xFF3B3835)
val surfaceContainerLowestDark = Color(0xFF0F0D0C)
val surfaceContainerLowDark = Color(0xFF1C1A18)
val surfaceContainerDark = Color(0xFF211E1C)
val surfaceContainerHighDark = Color(0xFF2B2826)
val surfaceContainerHighestDark = Color(0xFF363331)

/**
 * Material 3 dark color scheme for SuminoLab (Style D: Warm Charcoal + Amber Gold).
 */
val DarkColorScheme: ColorScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    primaryFixed = primaryFixedDark,
    onPrimaryFixed = onPrimaryFixedDark,
    primaryFixedDim = primaryFixedDimDark,
    onPrimaryFixedVariant = onPrimaryFixedVariantDark,
    secondaryFixed = secondaryFixedDark,
    onSecondaryFixed = onSecondaryFixedDark,
    secondaryFixedDim = secondaryFixedDimDark,
    onSecondaryFixedVariant = onSecondaryFixedVariantDark,
    tertiaryFixed = tertiaryFixedDark,
    onTertiaryFixed = onTertiaryFixedDark,
    tertiaryFixedDim = tertiaryFixedDimDark,
    onTertiaryFixedVariant = onTertiaryFixedVariantDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)
