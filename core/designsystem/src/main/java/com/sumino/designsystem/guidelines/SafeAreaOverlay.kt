package com.sumino.designsystem.guidelines

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 12-07-2026
 *
 * Tints the system-inset regions (status bar, navigation bar, display cutout, IME)
 * so it is obvious which content sits under the system UI in an edge-to-edge screen.
 * Best viewed on-device or in a preview with showSystemUi = true. Debug-only overlay.
 */
@Composable
fun SafeAreaOverlay(
    enabled: Boolean = true,
    insets: WindowInsets = WindowInsets.safeDrawing,
    color: Color = Color(0xFFFF9800).copy(alpha = 0.25f)
) {
    if (!enabled) return

    val layoutDirection = LocalLayoutDirection.current
    val padding = insets.asPaddingValues()
    val top = padding.calculateTopPadding()
    val bottom = padding.calculateBottomPadding()
    val start = padding.calculateStartPadding(layoutDirection)
    val end = padding.calculateEndPadding(layoutDirection)

    Box(Modifier.fillMaxSize()) {
        if (top > 0.dp) {
            Box(
                Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(top)
                    .background(color)
            )
        }
        if (bottom > 0.dp) {
            Box(
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(bottom)
                    .background(color)
            )
        }
        if (start > 0.dp) {
            Box(
                Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxHeight()
                    .width(start)
                    .background(color)
            )
        }
        if (end > 0.dp) {
            Box(
                Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight()
                    .width(end)
                    .background(color)
            )
        }
    }
}

@Preview(name = "SafeAreaOverlay", showSystemUi = true, device = Devices.PIXEL_4)
@Composable
fun SafeAreaOverlayPreview() {
    Box(Modifier.fillMaxSize().background(Color(0xFFECEFF1))) {
        SafeAreaOverlay(enabled = true)
    }
}
