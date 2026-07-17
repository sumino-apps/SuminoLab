/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sumino.designsystem.theme.SuminoLabTheme
import com.sumino.designsystem.theme.Theme


/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 7/17/2026 at 10:03 AM
 *
 * Material-Theme-Builder style color-roles chart. Renders the full
 * [SuminoLabTheme] color scheme — accents, containers, fixed colors, surfaces,
 * inverse roles and scrim — so the whole palette can be reviewed at a glance and
 * checked for consistency across light and dark. Every cell is filled with the
 * color of the token it represents; the label uses the paired token so it stays
 * readable (which also demonstrates each `on*` color in place).
 */
@Composable
fun ColorRolesChart(modifier: Modifier = Modifier) {
    val c = Theme.colorScheme
    val gap = 3.dp

    Surface(modifier = modifier.fillMaxWidth(), color = c.background) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(gap),
        ) {
            // ── Accents + Error ──────────────────────────────────────────────
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(gap)) {
                AccentColumn(Modifier.weight(1f), "Primary", c.primary, c.onPrimary)
                AccentColumn(Modifier.weight(1f), "Secondary", c.secondary, c.onSecondary)
                AccentColumn(Modifier.weight(1f), "Tertiary", c.tertiary, c.onTertiary)
                AccentColumn(Modifier.weight(1f), "Error", c.error, c.onError)
            }

            // ── Containers ───────────────────────────────────────────────────
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(gap)) {
                ContainerColumn(Modifier.weight(1f), "Primary", c.primaryContainer, c.onPrimaryContainer)
                ContainerColumn(Modifier.weight(1f), "Secondary", c.secondaryContainer, c.onSecondaryContainer)
                ContainerColumn(Modifier.weight(1f), "Tertiary", c.tertiaryContainer, c.onTertiaryContainer)
                ContainerColumn(Modifier.weight(1f), "Error", c.errorContainer, c.onErrorContainer)
            }

            // ── Fixed colors (Error has no fixed roles → 4th slot empty) ──────
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(gap)) {
                FixedColumn(
                    Modifier.weight(1f), "Primary",
                    c.primaryFixed, c.primaryFixedDim, c.onPrimaryFixed, c.onPrimaryFixedVariant,
                )
                FixedColumn(
                    Modifier.weight(1f), "Secondary",
                    c.secondaryFixed, c.secondaryFixedDim, c.onSecondaryFixed, c.onSecondaryFixedVariant,
                )
                FixedColumn(
                    Modifier.weight(1f), "Tertiary",
                    c.tertiaryFixed, c.tertiaryFixedDim, c.onTertiaryFixed, c.onTertiaryFixedVariant,
                )
                Spacer(Modifier.weight(1f))
            }

            // ── Surfaces (left 3/4) + inverse & scrim (right 1/4) ─────────────
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(gap)) {

                Column(Modifier.weight(3f), verticalArrangement = Arrangement.spacedBy(gap)) {

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(gap)) {
                        Cell("Surface Dim", c.surfaceDim, c.onSurface, Modifier.weight(1f), 72.dp)
                        Cell("Surface", c.surface, c.onSurface, Modifier.weight(1f), 72.dp)
                        Cell("Surface Bright", c.surfaceBright, c.onSurface, Modifier.weight(1f), 72.dp)
                    }

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(gap)) {
                        Cell("Surf. Container Lowest", c.surfaceContainerLowest, c.onSurface, Modifier.weight(1f), 60.dp)
                        Cell("Surf. Container Low", c.surfaceContainerLow, c.onSurface, Modifier.weight(1f), 60.dp)
                        Cell("Surf. Container", c.surfaceContainer, c.onSurface, Modifier.weight(1f), 60.dp)
                        Cell("Surf. Container High", c.surfaceContainerHigh, c.onSurface, Modifier.weight(1f), 60.dp)
                        Cell("Surf. Container Highest", c.surfaceContainerHighest, c.onSurface, Modifier.weight(1f), 60.dp)
                    }

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(gap)) {
                        Cell("On Surface", c.onSurface, c.surface, Modifier.weight(1f), 52.dp)
                        Cell("On Surface Var.", c.onSurfaceVariant, c.surfaceVariant, Modifier.weight(1f), 52.dp)
                        Cell("Outline", c.outline, c.surface, Modifier.weight(1f), 52.dp)
                        Cell("Outline Variant", c.outlineVariant, c.onSurface, Modifier.weight(1f), 52.dp)
                    }
                }

                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(gap)) {
                    Cell("Inverse Surface", c.inverseSurface, c.inverseOnSurface, Modifier.fillMaxWidth(), 60.dp)
                    Cell("Inverse On Surface", c.inverseOnSurface, c.inverseSurface, Modifier.fillMaxWidth(), 52.dp)
                    Cell("Inverse Primary", c.inversePrimary, c.onPrimaryContainer, Modifier.fillMaxWidth(), 52.dp)
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(gap)) {
                        Cell("Scrim", c.scrim, Color.White, Modifier.weight(1f), 52.dp)
                        Cell("Shadow", Color.Black, Color.White, Modifier.weight(1f), 52.dp)
                    }
                }
            }
        }
    }
}

/** A single filled swatch with its label drawn in [contentColor]. */
@Composable
private fun Cell(
    label: String,
    background: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    height: Dp = 52.dp,
) {
    Box(
        modifier = modifier
            .height(height)
            .background(background)
            .padding(horizontal = 8.dp, vertical = 6.dp),
    ) {
        Text(
            text = label,
            color = contentColor,
            style = Theme.typography.labelMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun AccentColumn(
    modifier: Modifier,
    role: String,
    roleColor: Color,
    onRoleColor: Color,
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Cell(role, roleColor, onRoleColor, Modifier.fillMaxWidth(), 56.dp)
        Cell("On $role", onRoleColor, roleColor, Modifier.fillMaxWidth(), 40.dp)
    }
}

@Composable
private fun ContainerColumn(
    modifier: Modifier,
    role: String,
    container: Color,
    onContainer: Color,
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Cell("$role Container", container, onContainer, Modifier.fillMaxWidth(), 56.dp)
        Cell("On $role Container", onContainer, container, Modifier.fillMaxWidth(), 44.dp)
    }
}

@Composable
private fun FixedColumn(
    modifier: Modifier,
    role: String,
    fixed: Color,
    fixedDim: Color,
    onFixed: Color,
    onFixedVariant: Color,
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(3.dp)) {
            Cell("$role Fixed", fixed, onFixed, Modifier.weight(1f), 48.dp)
            Cell("$role Fixed Dim", fixedDim, onFixed, Modifier.weight(1f), 48.dp)
        }
        Cell("On $role Fixed", onFixed, fixed, Modifier.fillMaxWidth(), 40.dp)
        Cell("On $role Fixed Variant", onFixedVariant, fixed, Modifier.fillMaxWidth(), 40.dp)
    }
}

// ── Previews ──────────────────────────────────────────────────────────────────

@Preview(name = "Color Roles — Light", showBackground = true, widthDp = 820)
@Composable
private fun ColorRolesLightPreview() {
    SuminoLabTheme(darkTheme = false) {
        ColorRolesChart()
    }
}

@Preview(name = "Color Roles — Dark", showBackground = true, widthDp = 820)
@Composable
private fun ColorRolesDarkPreview() {
    SuminoLabTheme(darkTheme = true) {
        ColorRolesChart()
    }
}
