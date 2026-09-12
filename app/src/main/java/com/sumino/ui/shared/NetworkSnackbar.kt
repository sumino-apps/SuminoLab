/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.ui.shared

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sumino.designsystem.theme.SuminoLabTheme
import kotlinx.coroutines.delay

/**
 * Animated network status banner.
 *
 * Displays when network is lost and briefly confirms when connectivity is restored.
 */
@Composable
fun NetworkSnackbar(
    isOnline: Boolean,
    modifier: Modifier = Modifier
) {
    var showBanner by remember { mutableStateOf(false) }
    var isOnlineBanner by remember { mutableStateOf(true) }

    LaunchedEffect(isOnline) {
        if (!isOnline) {
            isOnlineBanner = false
            showBanner = true
        } else if (showBanner) {
            isOnlineBanner = true
            delay(2500)
            showBanner = false
        }
    }

    AnimatedVisibility(
        visible = showBanner,
        enter = slideInVertically { -it },
        exit = slideOutVertically { -it },
        modifier = modifier
    ) {
        val backgroundColor = if (isOnlineBanner) {
            Color(0xFF2E7D32) // Material Green 800
        } else {
            MaterialTheme.colorScheme.error
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(12.dp),
            color = backgroundColor,
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (isOnlineBanner) Icons.Default.Wifi else Icons.Default.WifiOff,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = if (isOnlineBanner) "Back online" else "No internet connection",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NetworkSnackbarOfflinePreview() {
    SuminoLabTheme {
        NetworkSnackbar(isOnline = false)
    }
}
