package com.sumino.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Sizing tokens — icon sizes, component heights, avatar sizes and border widths.
 *
 * Material 3 has no size token, so SuminoLab exposes its own through
 * [LocalDimens] / `Theme.dimens`. [minTouchTarget] is the accessibility
 * floor (48dp) every tappable element must respect.
 */
@Immutable
data class Dimens(
    // Icon sizes
    val iconSmall: Dp = 16.dp,
    val iconMedium: Dp = 24.dp,
    val iconLarge: Dp = 40.dp,

    // Accessibility
    val minTouchTarget: Dp = 48.dp,

    // Component heights
    val buttonHeight: Dp = 48.dp,
    val textFieldHeight: Dp = 56.dp,
    val topAppBarHeight: Dp = 64.dp,
    val listItemHeight: Dp = 56.dp,

    // Avatar sizes
    val avatarSmall: Dp = 32.dp,
    val avatarMedium: Dp = 40.dp,
    val avatarLarge: Dp = 56.dp,

    // Border / stroke widths
    val borderThin: Dp = 1.dp,
    val borderThick: Dp = 2.dp,
)

val LocalDimens = staticCompositionLocalOf { Dimens() }
