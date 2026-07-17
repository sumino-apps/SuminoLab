package com.sumino.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Elevation scale mirroring the Material 3 elevation levels. Exposed through
 * [LocalElevation] / `Theme.elevation` for components that need a raw Dp
 * (shadows, custom surfaces) rather than a tonal surface color.
 */
@Immutable
data class Elevation(
    val none: Dp = 0.dp,
    val level1: Dp = 1.dp,
    val level2: Dp = 3.dp,
    val level3: Dp = 6.dp,
    val level4: Dp = 8.dp,
    val level5: Dp = 12.dp,
)

val LocalElevation = staticCompositionLocalOf { Elevation() }
