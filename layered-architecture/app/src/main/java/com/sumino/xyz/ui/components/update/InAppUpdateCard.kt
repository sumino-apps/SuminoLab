package com.sumino.xyz.ui.components.update

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 14-01-2026 at 10:48 AM
 */
@Composable
fun InAppUpdateCard(
    state: AppUpdateState,
    onClose: () -> Unit,
    onAction: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp, color = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box(Modifier.padding(16.dp)) {

            // Close button only in first state
            if (state == AppUpdateState.UPDATE_AVAILABLE) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(start = 8.dp)
                        .clickable { onClose() }
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    imageVector = when (state) {
                        AppUpdateState.RESTART -> Icons.Default.Refresh
                        else -> Icons.Default.Download
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp)
                )

                Spacer(Modifier.width(16.dp))

                Column {

                    Text(
                        text = when (state) {
                            AppUpdateState.RESTART ->
                                "One last step — restart your app to install new updates"

                            else ->
                                "Get new features and the latest experience"
                        },
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge,
                    )

                    Spacer(Modifier.height(12.dp))

                    when (state) {

                        AppUpdateState.DOWNLOADING -> {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(22.dp),
                                    strokeWidth = 2.dp
                                )
                                Spacer(Modifier.width(12.dp))
                                Text(
                                    "Downloading updates…",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    style = MaterialTheme.typography.labelSmall,
                                )
                            }
                        }

                        AppUpdateState.UPDATE_AVAILABLE -> {
                            Text(
                                "Update Google Pay",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.clickable { onAction() }
                            )
                        }

                        AppUpdateState.RESTART -> {
                            Text(
                                "Restart now",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.clickable { onAction() }
                            )
                        }
                    }
                }
            }
        }
    }
}
