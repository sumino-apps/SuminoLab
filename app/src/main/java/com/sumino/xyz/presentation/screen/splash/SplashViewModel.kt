package com.sumino.xyz.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumino.xyz.data.local.prefs.SharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 03-01-2026 at 12:49 PM
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharedPref: SharedPref
) : ViewModel() {

    fun isFirstLaunch(): Boolean {
        return sharedPref.isFirstTimeLaunch
    }

    private val _progress = MutableStateFlow(0)
    val progress: StateFlow<Int> = _progress

    private val totalTime = 3000L

    fun startTimer(onFinished: (Boolean) -> Unit) {
        viewModelScope.launch {
            var elapsed = 0L
            while (elapsed < totalTime) {
                delay(1000L)
                elapsed += 1000L
                _progress.value = ((elapsed * 100) / totalTime).toInt()
            }
            onFinished(sharedPref.isFirstTimeLaunch)
        }
    }

}
