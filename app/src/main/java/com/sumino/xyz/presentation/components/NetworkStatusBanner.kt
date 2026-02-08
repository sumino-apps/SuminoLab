package com.sumino.xyz.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 03-01-2026 at 1:10 PM
 */
@Composable
fun NetworkStatusBanner(
    isOnline: Boolean,
    showReconnectMessage: Boolean,
    modifier: Modifier = Modifier
) {
    val visible = !isOnline || showReconnectMessage

    val backgroundColor = when {
        !isOnline -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.primary
    }
    val textColor = when {
        !isOnline -> MaterialTheme.colorScheme.onError
        else -> MaterialTheme.colorScheme.onPrimary
    }
    val message = when {
        !isOnline -> "You’re offline"
        else -> "You’re back online"
    }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically { fullHeight -> +fullHeight },
        exit = slideOutVertically { fullHeight -> +fullHeight }
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(vertical = 5.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = message,
                color = textColor,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

