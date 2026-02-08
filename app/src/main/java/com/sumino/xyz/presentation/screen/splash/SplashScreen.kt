package com.sumino.xyz.presentation.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sumino.xyz.R
import com.sumino.xyz.destinations.HomeScreenDestination
import com.sumino.xyz.destinations.OnboardingScreenDestination
import com.sumino.xyz.destinations.SplashScreenDestination

/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 02-01-2026 at 9:57 PM
 */
@Destination<RootGraph>(start = true)
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val progress by viewModel.progress.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startTimer { isFirstLaunch ->
            if (isFirstLaunch) {
                navigator.navigate(OnboardingScreenDestination) {
                    popUpTo(route = SplashScreenDestination) {
                        inclusive = true
                    }
                }
            } else {
                navigator.navigate(HomeScreenDestination) {
                    popUpTo(SplashScreenDestination) {
                        inclusive = true
                    }
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-60).dp), // 35% from top
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.default_splash_logo),
                contentDescription = "Splash Logo",
                modifier = Modifier.size(150.dp)
            )


            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = stringResource(R.string.video_story_downloader),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LinearProgressIndicator(
                progress = { progress / 100f },
                modifier = Modifier.width(200.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                strokeCap = StrokeCap.Round
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.made_in_india),
                style = MaterialTheme.typography.bodySmall,
            )
        }

    }
}
