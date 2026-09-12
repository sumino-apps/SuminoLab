package com.sumino.designsystem.guidelines

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 12-07-2026
 *
 * Draws exact horizontal + vertical center lines with a small crosshair marker
 * to verify that content is truly centered. Debug-only overlay.
 */
@Composable
fun CenterLines(
    enabled: Boolean = true,
    color: Color = Color(0xFFE91E63).copy(alpha = 0.4f),
    strokeWidth: Dp = 1.dp,
    showHorizontal: Boolean = true,
    showVertical: Boolean = true,
    crosshairSize: Dp = 12.dp
) {
    if (!enabled) return

    Canvas(modifier = Modifier.fillMaxSize()) {
        val strokeWidthPx = strokeWidth.toPx()
        val cx = size.width / 2f
        val cy = size.height / 2f

        // Full-length center lines
        if (showVertical) {
            drawLine(color, Offset(cx, 0f), Offset(cx, size.height), strokeWidthPx)
        }
        if (showHorizontal) {
            drawLine(color, Offset(0f, cy), Offset(size.width, cy), strokeWidthPx)
        }

        // Bold crosshair marker at the exact center
        val half = crosshairSize.toPx() / 2f
        drawLine(color, Offset(cx - half, cy), Offset(cx + half, cy), strokeWidthPx * 2f)
        drawLine(color, Offset(cx, cy - half), Offset(cx, cy + half), strokeWidthPx * 2f)
    }
}

@Preview(name = "CenterLines", showBackground = true, widthDp = 240, heightDp = 320)
@Composable
fun CenterLinesPreview() {
    Box(Modifier.fillMaxSize().background(Color(0xFFECEFF1))) {
        CenterLines(enabled = true)
    }
}
