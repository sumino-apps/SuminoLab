package com.sumino.designsystem

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun isLandscape(): Boolean =
    LocalConfiguration.current.orientation ==
            Configuration.ORIENTATION_LANDSCAPE


@Composable
fun isPortrait(): Boolean =
    LocalConfiguration.current.orientation ==
            Configuration.ORIENTATION_PORTRAIT