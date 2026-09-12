/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.feature.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sumino.core.config.AppConfig
import com.sumino.core.theme.AppTheme
import com.sumino.designsystem.ColorRolesChart
import com.sumino.feature.home.state.HomeUiState
import com.sumino.ui.dialog.CenterAlertDialog
import com.sumino.ui.sheet.AppBottomSheetHost
import com.sumino.ui.sheet.rememberAppBottomSheet
import com.sumino.ui.utils.shimmerLoading

/**
 * Purely stateless Home screen composable.
 *
 * Serves as an interactive showcase of all Material 3 components and the design system's
 * live [ColorRolesChart], allowing real-device inspection in both Light and Dark themes.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bottomSheetController = rememberAppBottomSheet()
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Interactive Demo States
    var switchChecked by remember { mutableStateOf(true) }
    var checkboxChecked by remember { mutableStateOf(true) }
    var selectedRadio by remember { mutableIntStateOf(0) }
    var sliderValue by remember { mutableFloatStateOf(0.6f) }
    var textInput by remember { mutableStateOf("SuminoLab Design System") }
    var filterChipSelected by remember { mutableStateOf(true) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Palette,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "SuminoLab Showcase",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Theme Settings"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // ── Section: Header & Live Theme Banner ────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Material 3",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Button(
                            onClick = onNavigateToSettings
                        ) {
                            Icon(
                                imageVector = Icons.Default.Palette,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Theme", style = MaterialTheme.typography.labelMedium)
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Real-device preview of colors, typography, buttons, chips, and surfaces. Toggle Light/Dark mode in Settings.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                    )
                }
            }

            // ── Section 1: Live Color Roles Chart ──────────────────────────────
            SectionTitle(title = "1. Color Tokens & Roles (Live Palette)")
            Text(
                text = "Scroll horizontally to view all Material 3 color roles:",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(12.dp)
                ) {
                    ColorRolesChart(modifier = Modifier.width(760.dp))
                }
            }

            // ── Section 2: Material 3 Buttons & FABs ───────────────────────────
            SectionTitle(title = "2. Buttons & Actions")
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = {}) {
                    Text("Filled Button")
                }
                FilledTonalButton(onClick = {}) {
                    Text("Tonal Button")
                }
                ElevatedButton(onClick = {}) {
                    Text("Elevated")
                }
                OutlinedButton(onClick = {}) {
                    Text("Outlined")
                }
                TextButton(onClick = {}) {
                    Text("Text Button")
                }
            }

            // Icon buttons & FAB row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledIconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
                }
                OutlinedIconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = null)
                }
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
                }
                Spacer(modifier = Modifier.weight(1f))
                FloatingActionButton(
                    onClick = {},
                    shape = RoundedCornerShape(16.dp),
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                }
            }

            // ── Section 3: Cards & Surfaces ────────────────────────────────────
            SectionTitle(title = "3. Cards & Elevation Surfaces")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text("Filled Card", style = MaterialTheme.typography.titleSmall)
                        Spacer(Modifier.height(4.dp))
                        Text("High surface", style = MaterialTheme.typography.bodySmall)
                    }
                }
                ElevatedCard(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text("Elevated", style = MaterialTheme.typography.titleSmall)
                        Spacer(Modifier.height(4.dp))
                        Text("Tonal shadow", style = MaterialTheme.typography.bodySmall)
                    }
                }
                OutlinedCard(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text("Outlined", style = MaterialTheme.typography.titleSmall)
                        Spacer(Modifier.height(4.dp))
                        Text("Border role", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            // ── Section 4: Selection & Controls ────────────────────────────────
            SectionTitle(title = "4. Selection Controls & Sliders")
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Material 3 Switch", style = MaterialTheme.typography.bodyMedium)
                        Switch(
                            checked = switchChecked,
                            onCheckedChange = { switchChecked = it }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Material 3 Checkbox", style = MaterialTheme.typography.bodyMedium)
                        Checkbox(
                            checked = checkboxChecked,
                            onCheckedChange = { checkboxChecked = it }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Radio Option A", style = MaterialTheme.typography.bodyMedium)
                        RadioButton(
                            selected = selectedRadio == 0,
                            onClick = { selectedRadio = 0 }
                        )
                        Text("Radio Option B", style = MaterialTheme.typography.bodyMedium)
                        RadioButton(
                            selected = selectedRadio == 1,
                            onClick = { selectedRadio = 1 }
                        )
                    }

                    Column {
                        Text(
                            text = "Slider Value: ${(sliderValue * 100).toInt()}%",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Slider(
                            value = sliderValue,
                            onValueChange = { sliderValue = it }
                        )
                    }
                }
            }

            // ── Section 5: Chips ───────────────────────────────────────────────
            SectionTitle(title = "5. Chips")
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = filterChipSelected,
                    onClick = { filterChipSelected = !filterChipSelected },
                    label = { Text("Filter Chip") },
                    leadingIcon = if (filterChipSelected) {
                        { Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    } else null
                )
                AssistChip(
                    onClick = {},
                    label = { Text("Assist Chip") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                )
                SuggestionChip(
                    onClick = {},
                    label = { Text("Suggestion") }
                )
                InputChip(
                    selected = true,
                    onClick = {},
                    label = { Text("Input Chip") },
                    trailingIcon = {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                    }
                )
            }

            // ── Section 6: Text Fields ─────────────────────────────────────────
            SectionTitle(title = "6. Text Inputs")
            OutlinedTextField(
                value = textInput,
                onValueChange = { textInput = it },
                label = { Text("Outlined Text Field") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            TextField(
                value = textInput,
                onValueChange = { textInput = it },
                label = { Text("Filled Text Field") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            )

            // ── Section 7: Progress & Custom Sumino Components ─────────────────
            SectionTitle(title = "7. Progress & Custom SuminoLab Components")
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                LinearProgressIndicator(
                    progress = { sliderValue },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(36.dp))
                    // Shimmer skeleton demo
                    Box(
                        modifier = Modifier
                            .width(200.dp)
                            .height(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .shimmerLoading()
                    )
                }
            }

            // ── Section 8: Interactive Dialog & Bottom Sheet Demos ──────────────
            SectionTitle(title = "8. Dialogs & Bottom Sheet Triggers")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(imageVector = Icons.Outlined.Delete, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Open Dialog")
                }

                Button(
                    onClick = {
                        bottomSheetController.show {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "SuminoLab Bottom Sheet",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "This sheet uses AppBottomSheet with Material 3 insets and design-system surface tokens.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                Button(
                                    onClick = { bottomSheetController.hide() },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text("Dismiss Sheet")
                                }
                            }
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Layers, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Open Sheet")
                }
            }

            // ── Section 9: Connectivity & Build Info ───────────────────────────
            SectionTitle(title = "9. App & Network Status")
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (uiState.isOnline) {
                        MaterialTheme.colorScheme.surfaceContainerHigh
                    } else {
                        MaterialTheme.colorScheme.errorContainer
                    }
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (uiState.isOnline) Icons.Default.CheckCircle else Icons.Default.Warning,
                        contentDescription = null,
                        tint = if (uiState.isOnline) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (uiState.isOnline) "Network Connected" else "Offline Mode",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = "Build: ${AppConfig.versionLabel} • ${AppConfig.buildType}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // Centered Alert Dialog Triggered State
    if (showDeleteDialog) {
        CenterAlertDialog(
            title = "Test CenterAlertDialog",
            message = "This confirmation dialog is rendered using Material 3 design tokens. You can test confirm and dismiss actions.",
            icon = Icons.Outlined.Info,
            onDismiss = { showDeleteDialog = false },
            onConfirm = { showDeleteDialog = false }
        )
    }

    // Bottom Sheet Host
    AppBottomSheetHost(controller = bottomSheetController)
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(top = 4.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            uiState = HomeUiState(isOnline = true),
            onNavigateToSettings = {}
        )
    }
}
