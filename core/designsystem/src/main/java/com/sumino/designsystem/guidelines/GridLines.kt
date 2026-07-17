package com.sumino.designsystem.guidelines

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 25-01-2026 at 11:12 AM
 */
@Composable
fun GridLines(
    enabled: Boolean = true,
    gridSize: Dp = 8.dp,
    color: Color = Color(0xFF2196F3).copy(alpha = 0.2f),
    strokeWidth: Dp = 1.dp,
    showHorizontal: Boolean = true,
    showVertical: Boolean = true
) {
    if (!enabled) return

    Canvas(modifier = Modifier.fillMaxSize()) {
        val gridSizePx = gridSize.toPx()
        val strokeWidthPx = strokeWidth.toPx()

        // Vertical lines
        if (showVertical) {
            var x = gridSizePx
            while (x < size.width) {
                drawLine(
                    color = color,
                    start = Offset(x, 0f),
                    end = Offset(x, size.height),
                    strokeWidth = strokeWidthPx
                )
                x += gridSizePx
            }
        }

        // Horizontal lines
        if (showHorizontal) {
            var y = gridSizePx
            while (y < size.height) {
                drawLine(
                    color = color,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidthPx
                )
                y += gridSizePx
            }
        }
    }
}