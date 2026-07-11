package com.sumino.xyz.ui.components.guidelines

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sumino.xyz.BuildConfig

/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 25-01-2026 at 8:38 AM
 */

// Enum for padding sides
enum class PaddingSide {
    Left, Right, Top, Bottom
}

// Unified composable
@Composable
fun PaddingGuideline(
    side: PaddingSide,
    modifier: Modifier = Modifier,
    blockSize: Dp = 16.dp,
    color: Color = Color(0xFF4CAF50).copy(alpha = 0.25f)
) {
    val boxModifier = when (side) {
        PaddingSide.Left, PaddingSide.Right -> modifier
            .fillMaxHeight()
            .width(blockSize)
            .background(color)

        PaddingSide.Top, PaddingSide.Bottom -> modifier
            .fillMaxWidth()
            .height(blockSize)
            .background(color)
    }

    Box(modifier = boxModifier)
}

// Wrapper composable with BuildConfig control
@Composable
fun BoxScope.PaddingGuidelines(
    enabled: Boolean = BuildConfig.DEBUG,
    blockSize: Dp = 16.dp,
    color: Color = Color(0xFF4CAF50).copy(alpha = 0.25f)
) {
    if (!enabled) return

    PaddingGuideline(
        side = PaddingSide.Left,
        blockSize = blockSize,
        color = color
    )

    PaddingGuideline(
        side = PaddingSide.Right,
        modifier = Modifier.align(Alignment.CenterEnd),
        blockSize = blockSize,
        color = color
    )

    PaddingGuideline(
        side = PaddingSide.Top,
        modifier = Modifier.align(Alignment.TopCenter),
        blockSize = blockSize,
        color = color
    )

    PaddingGuideline(
        side = PaddingSide.Bottom,
        modifier = Modifier.align(Alignment.BottomCenter),
        blockSize = blockSize,
        color = color
    )
}