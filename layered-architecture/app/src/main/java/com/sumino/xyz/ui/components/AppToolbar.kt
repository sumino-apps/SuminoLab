package com.sumino.xyz.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 08-01-2026 at 8:19 PM
 */

/*@Preview(showBackground = true)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true
)
@Composable
fun AppToolbarPreview() {
    SuminoLabTheme {
        AppToolbar(
            navigation = {
                IconButton(onClick = {

                }) {
                    Icon(Icons.Default.ArrowBack, null)
                }
            },
            title = {
                Text("Dashboard", style = MaterialTheme.typography.titleMedium)
            },
            actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Notifications, null)
                }
            }
        )

    }
}*/


@Composable
fun AppToolbar(
    modifier: Modifier = Modifier,
    navigation: @Composable (() -> Unit)? = null,
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    Surface(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            navigation?.invoke() ?: Spacer(Modifier.width(48.dp))

            Box(
                modifier = Modifier.weight(1f)
            ) {
                title()
            }

            Row(content = actions)
        }
    }
}

