package com.sumino.xyz.presentation.screen.onboarding

import androidx.lifecycle.ViewModel
import com.sumino.xyz.data.local.prefs.SharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 03-01-2026 at 1:01 PM
 */
@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val sharedPref: SharedPref
) : ViewModel() {

    /**
     * Call this when onboarding is completed
     */
    fun completeOnboarding() {
        sharedPref.isFirstTimeLaunch = false
    }
}
