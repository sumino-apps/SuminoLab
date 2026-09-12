package com.sumino.designsystem.guidelines

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sumino.designsystem.theme.SuminoLabTheme

/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 12-07-2026
 *
 * Runtime controller for the debug design-guideline overlays. Instead of gating each
 * overlay on a compile-time flag, [DesignOverlay] draws whichever guidelines are turned
 * on in [GuidelineState], and [GuidelineToggle] gives an on-screen panel to flip them.
 */

// Single source of truth for which guidelines are visible
class GuidelineState {
    var enabled by mutableStateOf(true)   // master switch
    var grid by mutableStateOf(false)
    var centerLines by mutableStateOf(false)
    var columns by mutableStateOf(false)
    var padding by mutableStateOf(false)
    var safeArea by mutableStateOf(false)
    var columnCount by mutableIntStateOf(4)
}

@Composable
fun rememberGuidelineState(): GuidelineState = remember { GuidelineState() }

val LocalGuidelineState = staticCompositionLocalOf { GuidelineState() }

/**
 * Wraps [content] and draws the enabled guidelines on top of it. The individual overlays
 * are forced on here because visibility is already controlled by [state]; that makes
 * [state] the only switch, so an internal QA build can flip guidelines on in any variant.
 */
@Composable
fun DesignOverlay(
    modifier: Modifier = Modifier,
    state: GuidelineState = LocalGuidelineState.current,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier) {
        content()

        if (state.enabled) {
            if (state.grid) GridLines(enabled = true)
            if (state.centerLines) CenterLines(enabled = true)
            if (state.columns) VerticalBoxes(enabled = true, divisions = state.columnCount)
            if (state.padding) PaddingGuidelines(enabled = true)
            if (state.safeArea) SafeAreaOverlay(enabled = true)
        }
    }
}

/**
 * Floating panel to toggle guidelines at runtime. Never shown in release builds.
 */
@Composable
fun BoxScope.GuidelineToggle(
    state: GuidelineState,
    alignment: Alignment = Alignment.BottomEnd
) {

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .align(alignment)
            .padding(12.dp),
        horizontalAlignment = Alignment.End
    ) {
        AnimatedVisibility(visible = expanded) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                tonalElevation = 6.dp,
                shadowElevation = 6.dp,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Column(Modifier.padding(8.dp)) {
                    TogglePill("Grid", state.grid) { state.grid = !state.grid }
                    TogglePill("Center", state.centerLines) { state.centerLines = !state.centerLines }
                    TogglePill("Columns", state.columns) { state.columns = !state.columns }
                    TogglePill("Padding", state.padding) { state.padding = !state.padding }
                    TogglePill("Safe area", state.safeArea) { state.safeArea = !state.safeArea }
                }
            }
        }

        FilledIconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.Build,
                contentDescription = "Toggle design guidelines"
            )
        }
    }
}

@Composable
private fun TogglePill(
    label: String,
    checked: Boolean,
    onToggle: () -> Unit
) {
    Surface(
        onClick = onToggle,
        shape = RoundedCornerShape(8.dp),
        color = if (checked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
        contentColor = if (checked) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Preview(name = "DesignOverlay + Toggle", showBackground = true, widthDp = 260, heightDp = 360)
@Composable
fun DesignGuidelinesPreview() {
    SuminoLabTheme {
        val state = rememberGuidelineState().apply {
            enabled = true
            grid = true
            centerLines = true
            columns = true
        }
        DesignOverlay(
            modifier = Modifier.fillMaxSize(),
            state = state
        ) {
            Box(Modifier.fillMaxSize().background(Color(0xFFECEFF1)))
            GuidelineToggle(state = state)
        }
    }
}
