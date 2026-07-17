package com.sumino.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

/**
 * Single entry point for reading SuminoLab design tokens inside composables.
 *
 * Material 3 tokens stay on [MaterialTheme] and are re-exposed here for a uniform
 * call site; the tokens Material 3 does not model — spacing, elevation, motion —
 * are read from their [androidx.compose.runtime.CompositionLocal]s.
 *
 * Usage:
 * ```
 * val gap = Theme.spacing.medium
 * val color = Theme.colorScheme.primary
 * ```
 */
object Theme {

    val colorScheme
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme

    val typography
        @Composable @ReadOnlyComposable get() = MaterialTheme.typography

    val shapes
        @Composable @ReadOnlyComposable get() = MaterialTheme.shapes

    val spacing
        @Composable @ReadOnlyComposable get() = LocalSpacing.current

    val elevation
        @Composable @ReadOnlyComposable get() = LocalElevation.current

    val motion
        @Composable @ReadOnlyComposable get() = LocalMotion.current

    val dimens
        @Composable @ReadOnlyComposable get() = LocalDimens.current

    /** State-layer / disabled opacity constants (not themed). */
    val alpha get() = Alpha
}
