package com.sumino.xyz.ui.screen.home


import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sumino.xyz.ui.feature.crash.CrashActivity
import com.sumino.xyz.ui.feature.feedback.FeedbackActivity
import com.sumino.xyz.ui.components.update.AppUpdateState
import com.sumino.xyz.ui.components.update.InAppUpdateCard

/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 02-01-2026 at 9:58 PM
 */
@Destination<RootGraph>
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navigator: DestinationsNavigator, viewModel: HomeViewModel = hiltViewModel()) {
    val context = LocalContext.current


    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Welcome to Home Screen! 🎉",
                style = MaterialTheme.typography.headlineSmall
            )

            Button(
                onClick = {
                    val intent = Intent(context, CrashActivity::class.java)
                    context.startActivity(intent)
                }
            ) {
                Text("Crash Activity")
            }

            Button(
                onClick = {
                    val intent = Intent(context, FeedbackActivity::class.java)
                    context.startActivity(intent)
                }
            ) {
                Text("Feedback Activity")
            }

            Button(
                onClick = {

                }
            ) {
                Text("Show Dialog")
            }

            UpdateDemo()




        }

    }
}

@Composable
fun UpdateDemo() {
    var updateState by remember {
        mutableStateOf(AppUpdateState.UPDATE_AVAILABLE)
    }

    InAppUpdateCard(
        state = updateState,
        onClose = { /* dismiss */ },
        onAction = {
            updateState = when (updateState) {
                AppUpdateState.UPDATE_AVAILABLE -> AppUpdateState.DOWNLOADING
                AppUpdateState.DOWNLOADING -> AppUpdateState.RESTART
                AppUpdateState.RESTART -> AppUpdateState.UPDATE_AVAILABLE
            }
        }
    )

}