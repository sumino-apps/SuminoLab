package com.sumino.designsystem.theme

import android.app.Activity
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
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

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

    SetSystemBarIcons(darkTheme)

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

@Composable
fun SetSystemBarIcons(isDarkIcons: Boolean = isSystemInDarkTheme()) {
    val view = LocalView.current

    SideEffect {
        val window = (view.context as Activity).window
        WindowCompat.getInsetsController(window, view)
            .isAppearanceLightStatusBars = !isDarkIcons
    }
}
