package com.sumino.xyz.ui.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.edit
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import com.sumino.xyz.ui.components.NetworkStatusBanner
import com.sumino.xyz.ui.navigation.AppNavGraph
import com.sumino.xyz.ui.theme.SetStatusBarColor
import com.sumino.xyz.ui.theme.SuminoLabTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), DefaultLifecycleObserver {

    override fun onCreate(savedInstanceState: Bundle?) {
        super<ComponentActivity>.onCreate(savedInstanceState)
        lifecycle.addObserver(this)


        getSharedPreferences("crash_guard_prefs", MODE_PRIVATE)
            .edit {
                remove("last_crash_time")
            }

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    moveTaskToBack(false)
                }
            }
        )

        setContent {
            val mainViewModel: MainViewModel = hiltViewModel()
            val state by mainViewModel.state.collectAsState()

            SuminoLabTheme {
                SetStatusBarColor()
                Scaffold(
                    bottomBar = {
                        NetworkStatusBanner(
                            isOnline = state.isOnline,
                            showReconnectMessage = state.showReconnectMessage
                        )
                    }) { innerPadding ->

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        AppNavGraph()

//                        PaddingGuidelines()

//                        GridLines()

//                        VerticalBoxes(divisions = 9)

//                        VerticalDividers(
//                            divisions = 4,
//                            dividerWidth = 8.dp  // White space ki width
//                        )


                    }
                }
            }
        }
    }


    override fun onDestroy() {
        lifecycle.removeObserver(this)
        super<ComponentActivity>.onDestroy()
    }


}