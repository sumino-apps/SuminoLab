package com.sumino.designsystem.theme

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Single source of truth for whether the active composition is in Dark Mode.
 */
val LocalDarkTheme = compositionLocalOf { false }

/**
 * Safely unwrap Context to find the hosting Activity without ClassCastException.
 */
tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
/**
 * Root theme for every SuminoLab app.
 *
 * This is a *configurable* core theme, not a fixed one: every token group is a
 * parameter defaulting to the platform value, so an app overrides only what it
 * needs and inherits the rest. Partial overrides use `copy(...)`:
 * ```
 * SuminoLabTheme(
 *     lightColors = LightColorScheme.copy(primary = BrandBlue),
 *     spacing = Spacing(medium = 20.dp),
 * ) { AppContent() }
 * ```
 * Material 3 tokens are read via [Theme] (e.g. `Theme.colorScheme`); the extra
 * tokens (spacing/elevation/motion/dimens) via `Theme.spacing` etc.
 *
 * @param darkTheme whether to use the dark color scheme.
 * @param dynamicColor use Android 12+ wallpaper-based colors; when on, it takes
 *   precedence over [lightColors] / [darkColors].
 * @param lightColors color scheme applied in light mode.
 * @param darkColors color scheme applied in dark mode.
 * @param typography type scale for the app.
 * @param shapes corner-shape scale for the app.
 * @param spacing spacing scale exposed via [Theme.spacing].
 * @param elevation elevation scale exposed via [Theme.elevation].
 * @param motion motion tokens exposed via [Theme.motion].
 * @param dimens sizing tokens exposed via [Theme.dimens].
 */
@Composable
fun SuminoLabTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    lightColors: ColorScheme = LightColorScheme,
    darkColors: ColorScheme = DarkColorScheme,
    typography: Typography = Typography,
    shapes: Shapes = Shapes,
    spacing: Spacing = Spacing(),
    elevation: Elevation = Elevation(),
    motion: Motion = Motion(),
    dimens: Dimens = Dimens(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColors
        else -> lightColors
    }

    SetSystemBarIcons(darkIcons = !darkTheme)

    CompositionLocalProvider(
        LocalSpacing provides spacing,
        LocalElevation provides elevation,
        LocalMotion provides motion,
        LocalDimens provides dimens,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            shapes = shapes,
            content = content,
        )
    }
}

/**
 * Configures status bar and navigation bar icon appearance safely.
 *
 * @param darkIcons If true, icons/text are dark (e.g. black, for light backgrounds).
 *                  If false, icons/text are light (e.g. white, for dark backgrounds).
 */
@Composable
fun SetSystemBarIcons(darkIcons: Boolean = !isSystemInDarkTheme()) {
    val view = LocalView.current
    if (view.isInEditMode) return

    SideEffect {
        val window = view.context.findActivity()?.window ?: return@SideEffect
        val insetsController = WindowCompat.getInsetsController(window, view)
        insetsController.isAppearanceLightStatusBars = darkIcons
        insetsController.isAppearanceLightNavigationBars = darkIcons
    }
}

/**
 * Convenience helper that forces system bar icons to be white/light for dark/immersive screens
 * (Camera, PhotoViewer, Crop, Editor, Paywall).
 */
@Composable
fun SetImmersiveDarkScreenSystemBars() {
    SetSystemBarIcons(darkIcons = false)
}

/**
 * Overrides system bar icon appearance temporarily for a specific screen (e.g. Crop, PhotoViewer, Camera)
 * and safely restores the active app theme state when the screen leaves composition.
 *
 * @param isDarkIcons false for light/white icons on dark backgrounds, true for dark icons on light backgrounds.
 */
@Composable
fun SetTransientSystemBarIcons(isDarkIcons: Boolean = isSystemInDarkTheme()) {
    val view = LocalView.current
    if (view.isInEditMode) return
    val isAppDark = LocalDarkTheme.current

    DisposableEffect(isDarkIcons, isAppDark) {
        val window = view.context.findActivity()?.window
        val insetsController = window?.let { WindowCompat.getInsetsController(it, view) }

        // Preserves backwards compatibility with callers passing isDarkIcons = true for white icons on dark screens
        insetsController?.isAppearanceLightStatusBars = !isDarkIcons
        insetsController?.isAppearanceLightNavigationBars = !isDarkIcons

        onDispose {
            // Restore to current active app theme without relying on fragile cached previousState
            insetsController?.isAppearanceLightStatusBars = !isAppDark
            insetsController?.isAppearanceLightNavigationBars = !isAppDark
        }
    }
}
