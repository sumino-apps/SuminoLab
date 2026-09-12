package com.sumino.designsystem.guidelines

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 12-07-2026
 *
 * Reusable debug modifier that outlines any composable's bounds, similar to the
 * system "Show layout bounds" developer option but scoped to where you apply it.
 * Pass a [tag] to give nested composables distinct colors. Debug-only, no-op in release.
 */

// Distinct color per tag so nested / repeated bounds are easy to tell apart
object DebugBoundsPalette {
    private val colors = listOf(
        Color(0xFFF44336), // red
        Color(0xFF2196F3), // blue
        Color(0xFF4CAF50), // green
        Color(0xFFFF9800), // orange
        Color(0xFF9C27B0), // purple
        Color(0xFF00BCD4)  // cyan
    )

    fun colorFor(tag: Any?): Color {
        val index = ((tag?.hashCode() ?: 0) and Int.MAX_VALUE) % colors.size
        return colors[index].copy(alpha = 0.6f)
    }
}

fun Modifier.debugBounds(
    enabled: Boolean = true,
    color: Color = DebugBoundsPalette.colorFor(null),
    width: Dp = 1.dp
): Modifier = if (enabled) this.border(width, color) else this

fun Modifier.debugBounds(
    tag: Any?,
    enabled: Boolean = true,
    width: Dp = 1.dp
): Modifier = debugBounds(
    enabled = enabled,
    color = DebugBoundsPalette.colorFor(tag),
    width = width
)

@Preview(name = "debugBounds", showBackground = true, widthDp = 240)
@Composable
fun DebugBoundsPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(Modifier.fillMaxWidth().height(40.dp).debugBounds(tag = "header", enabled = true)) {}
        Column(Modifier.fillMaxWidth().height(60.dp).debugBounds(tag = "body", enabled = true)) {}
        Column(
            Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Color(0xFFECEFF1))
                .debugBounds(tag = "footer", enabled = true)
        ) {}
    }
}
