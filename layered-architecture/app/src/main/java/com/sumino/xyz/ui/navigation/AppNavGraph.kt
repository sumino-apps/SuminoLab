package com.sumino.xyz.ui.navigation

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.DestinationsNavHost
import com.sumino.xyz.NavGraphs


/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 02-01-2026 at 9:54 PM
 */
@Composable
fun AppNavGraph(){
    DestinationsNavHost(navGraph = NavGraphs.root)
}