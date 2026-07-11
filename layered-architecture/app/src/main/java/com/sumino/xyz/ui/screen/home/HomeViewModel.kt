package com.sumino.xyz.ui.screen.home

import androidx.lifecycle.ViewModel
import com.sumino.xyz.data.local.prefs.SharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 03-01-2026 at 1:03 PM
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sharedPref: SharedPref
) : ViewModel() {

}