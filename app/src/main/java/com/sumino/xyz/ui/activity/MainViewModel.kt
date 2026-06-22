package com.sumino.xyz.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumino.xyz.core.network.NetworkMonitor
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
 * Created 03-01-2026 at 12:40 PM
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPref: SharedPref, networkMonitor: NetworkMonitor
) : ViewModel() {
    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state

    init {
        observeNetwork(networkMonitor)
    }

    private fun observeNetwork(networkMonitor: NetworkMonitor) {
        viewModelScope.launch {
            networkMonitor.isOnline.collect { online ->

                val wasOffline = !_state.value.isOnline

                _state.value = _state.value.copy(
                    isOnline = online,
                    showReconnectMessage = online && wasOffline
                )

                if (online && wasOffline) {
                    delay(2000L)
                    _state.value = _state.value.copy(
                        showReconnectMessage = false
                    )
                }
            }
        }
    }
}