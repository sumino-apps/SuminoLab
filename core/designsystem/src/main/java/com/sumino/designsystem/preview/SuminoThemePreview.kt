package com.sumino.designsystem.preview

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.sumino.designsystem.theme.SuminoLabTheme

/**
 * Wraps preview [content] in [SuminoLabTheme] on a themed [Surface] so
 * design-system previews always render with the correct tokens.
 *
 * Usage:
 * ```
 * @MultiDevicePreview
 * @Composable
 * private fun MyComponentPreview() = SuminoThemePreview { MyComponent() }
 * ```
 */
@Composable
fun SuminoThemePreview(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    SuminoLabTheme(darkTheme = darkTheme) {
        Surface(content = content)
    }
}
