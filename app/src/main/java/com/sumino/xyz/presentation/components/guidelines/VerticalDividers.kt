package com.sumino.xyz.presentation.components.guidelines


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sumino.xyz.BuildConfig

/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 25-01-2026 at 11:25 AM
 */
@Composable
fun VerticalDividers(
    enabled: Boolean = BuildConfig.DEBUG,
    divisions: Int = 4,
    boxColor: Color = Color(0xFF4CAF50).copy(alpha = 0.25f),
    dividerWidth: Dp = 8.dp
) {
    if (!enabled) return

    Canvas(modifier = Modifier.fillMaxSize()) {
        val dividerWidthPx = dividerWidth.toPx()
        val totalDividerWidth = dividerWidthPx * (divisions - 1)
        val availableWidth = size.width - totalDividerWidth
        val boxWidth = availableWidth / divisions

        var currentX = 0f

        for (i in 0 until divisions) {
            // Draw green box
            drawRect(
                color = boxColor,
                topLeft = Offset(currentX, 0f),
                size = Size(boxWidth, size.height)
            )
            currentX += boxWidth

            // Draw white divider (except after last box)
            if (i < divisions - 1) {
                currentX += dividerWidthPx
            }
        }
    }
}