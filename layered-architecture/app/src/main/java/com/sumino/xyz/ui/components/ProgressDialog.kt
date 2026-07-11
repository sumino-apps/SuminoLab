package com.sumino.xyz.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.spec.DestinationStyle


/*@Preview(showBackground = true)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true
)
@Composable
fun ProgressDialogPreview() {
    SuminoLabTheme {
        ProgressMessage(
            title = "Processing",
            message = "This may take a few seconds",
            onCloseClicked = {
                // cancel API / dismiss dialog
            }
        )

    }
}*/

@Destination<RootGraph>(style = DestinationStyle.Dialog::class)
@Composable
fun ProgressDialog() {

    Dialog(onDismissRequest = { }) {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraLarge)
                .background(MaterialTheme.colorScheme.surface)
                .padding(32.dp)
        ) {
            CircularProgressIndicator(
                strokeCap = StrokeCap.Round,
                strokeWidth = 5.dp,
                modifier = Modifier.size(60.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }

}

@Composable
fun CenterProgress(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            strokeCap = StrokeCap.Round,
            modifier = Modifier.size(60.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            strokeWidth = 5.dp,
        )
    }
}

@Composable
fun ProgressMessage(
    title: String = "Action In Progress..",
    message: String,
    onCloseClicked: (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.surface,
) {
    ScreenMessage(
        title = title,
        message = message,
        onDismiss = onCloseClicked,
        containerColor = containerColor,
        icon = {
            CircularProgressIndicator(
                modifier = Modifier.size(36.dp),
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        },
        button = {}
    )
}