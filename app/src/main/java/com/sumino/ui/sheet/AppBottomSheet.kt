/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.ui.sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Configuration options for [AppBottomSheet].
 */
@Stable
data class SheetConfig(
    val skipPartiallyExpanded: Boolean = true,
    val showDragHandle: Boolean = true
)

/**
 * Controller allowing programmatic control over bottom sheet presentation and contents.
 */
@Stable
class BottomSheetController internal constructor(
    private val coroutineScope: CoroutineScope,
    @OptIn(ExperimentalMaterial3Api::class)
    val sheetState: SheetState
) {
    var isVisible by mutableStateOf(false)
        private set

    var currentContent by mutableStateOf<(@Composable ColumnScope.() -> Unit)?>(null)
        private set

    var currentConfig by mutableStateOf(SheetConfig())
        private set

    @OptIn(ExperimentalMaterial3Api::class)
    fun show(
        config: SheetConfig = SheetConfig(),
        content: @Composable ColumnScope.() -> Unit
    ) {
        currentConfig = config
        currentContent = content
        isVisible = true
        coroutineScope.launch {
            sheetState.show()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun hide() {
        coroutineScope.launch {
            sheetState.hide()
            isVisible = false
            currentContent = null
        }
    }
}

/**
 * Creates and remembers a [BottomSheetController].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberAppBottomSheet(): BottomSheetController {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    return remember(coroutineScope, sheetState) {
        BottomSheetController(coroutineScope, sheetState)
    }
}

/**
 * Host component to place once at screen root to render any sheet invoked by [controller].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomSheetHost(
    controller: BottomSheetController,
    modifier: Modifier = Modifier
) {
    if (controller.isVisible && controller.currentContent != null) {
        AppBottomSheet(
            onDismiss = { controller.hide() },
            config = controller.currentConfig,
            sheetState = controller.sheetState,
            modifier = modifier,
            content = controller.currentContent!!
        )
    }
}

/**
 * Material 3 modal bottom sheet foundation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    config: SheetConfig = SheetConfig(),
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = config.skipPartiallyExpanded),
    content: @Composable ColumnScope.() -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier,
        sheetState = sheetState,
        dragHandle = if (config.showDragHandle) {
            { BottomSheetDefaults.DragHandle() }
        } else null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .imePadding()
                .padding(bottom = 16.dp),
            content = content
        )
    }
}
