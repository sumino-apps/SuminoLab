package com.sumino.xyz.presentation.components.guidelines

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sumino.xyz.BuildConfig

/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 25-01-2026 at 11:19 AM
 */
@Composable
fun VerticalBoxes(
    enabled: Boolean = BuildConfig.DEBUG,
    divisions: Int = 4,
    color: Color = Color(0xFF4CAF50).copy(alpha = 0.15f),
    borderColor: Color = Color(0xFF4CAF50).copy(alpha = 0.5f),
    borderWidth: Dp = 1.dp
) {
    if (!enabled) return

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        repeat(divisions) { index ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .border(
                        width = borderWidth,
                        color = borderColor
                    )
                    .background(
                        if (index % 2 == 0) color else Color.Transparent
                    )
            )
        }
    }
}