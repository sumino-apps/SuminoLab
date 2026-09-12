/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.core.connectivity

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Universal network connectivity monitor.
 *
 * Exposes a reactive [isOnline] flow and synchronous/suspend status helpers.
 * Robust against OEM-specific race conditions (e.g., Samsung cold start where
 * activeNetwork may briefly be null despite active WiFi).
 */
@Singleton
class NetworkMonitor @Inject constructor(
    private val connectivityManager: ConnectivityManager
) {

    /**
     * Cold-start resilient flow emitting true when a network with Internet capability is active.
     */
    val isOnline: Flow<Boolean> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(hasInternetCapability(network))
            }

            override fun onLost(network: Network) {
                trySend(isConnected())
            }

            override fun onCapabilitiesChanged(
                network: Network,
                capabilities: NetworkCapabilities
            ) {
                trySend(isCapable(capabilities))
            }
        }

        trySend(isConnected())

        connectivityManager.registerDefaultNetworkCallback(callback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged().conflate()

    private fun hasInternetCapability(network: Network): Boolean {
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return isCapable(capabilities)
    }

    private fun isCapable(capabilities: NetworkCapabilities): Boolean {
        val hasInternet = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        val hasTransport = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        return hasInternet && hasTransport
    }

    /**
     * Synchronous check for active internet connection.
     * Checks both activeNetwork and all available networks to handle OEM cold-start lag.
     */
    fun isConnected(): Boolean {
        val activeCaps = connectivityManager.activeNetwork
            ?.let { connectivityManager.getNetworkCapabilities(it) }
        if (activeCaps != null && isCapable(activeCaps)) return true

        @Suppress("DEPRECATION")
        return connectivityManager.allNetworks.any { network ->
            connectivityManager.getNetworkCapabilities(network)?.let(::isCapable) == true
        }
    }

    /**
     * Suspending check that fetches the first emission from [isOnline].
     */
    suspend fun isCurrentlyOnline(): Boolean = isOnline.first()

    /**
     * True if the current active network is Wi-Fi or Ethernet.
     */
    fun isWifi(): Boolean {
        val caps = connectivityManager.activeNetwork
            ?.let { connectivityManager.getNetworkCapabilities(it) } ?: return false
        return caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
}
