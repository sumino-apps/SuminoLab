package com.sumino.designsystem.theme

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Motion tokens — standard animation durations (in milliseconds) and easing
 * curves, so every animation across the platform feels consistent. Exposed
 * through [LocalMotion] / `Theme.motion`.
 */
@Immutable
data class Motion(
    val durationFast: Int = 150,
    val durationMedium: Int = 300,
    val durationSlow: Int = 500,
    val standardEasing: Easing = CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f),
    val emphasizedEasing: Easing = CubicBezierEasing(0.05f, 0.7f, 0.1f, 1.0f),
)

val LocalMotion = staticCompositionLocalOf { Motion() }
