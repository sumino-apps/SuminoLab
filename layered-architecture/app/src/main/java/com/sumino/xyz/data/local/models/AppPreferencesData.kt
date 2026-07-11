package com.sumino.xyz.data.local.models

import kotlinx.serialization.Serializable

/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 06-01-2026 at 1:46 PM
 */
@Serializable
data class AppPreferencesData(
    var isFirstTimeLaunch : Boolean = true,
    var firstLaunchTime : Long = 0L,
    var dialogPreviouslyShown : Boolean = false,
)