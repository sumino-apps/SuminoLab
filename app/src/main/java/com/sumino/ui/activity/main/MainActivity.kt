package com.sumino.ui.activity.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.sumino.core.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main host activity for the application.
 *
 * Hosts the root [com.sumino.core.theme.AppTheme] and [MainNavHost].
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    MainNavHost()
                }
            }
        }
    }
}