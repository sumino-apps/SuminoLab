package com.sumino.xyz.ui.screen.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sumino.xyz.destinations.HomeScreenDestination
import com.sumino.xyz.destinations.OnboardingScreenDestination

/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 02-01-2026 at 9:57 PM
 */
@Destination<RootGraph>
@Composable
fun OnboardingScreen(
    navigator: DestinationsNavigator,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome 👋")

        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            viewModel.completeOnboarding()
            navigator.navigate(HomeScreenDestination) {
                popUpTo(OnboardingScreenDestination) {
                    inclusive = true
                }
            }
        }) {
            Text("Get Started")
        }
    }
}