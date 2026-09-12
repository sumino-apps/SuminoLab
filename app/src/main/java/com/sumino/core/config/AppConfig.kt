/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.core.config

import com.sumino.BuildConfig

/**
 * Single source of truth for build-time configuration.
 * Decouples screens and ViewModels from generated [BuildConfig] fields.
 */
object AppConfig {

    // ---- Build identity ----
    val applicationId: String = BuildConfig.APPLICATION_ID
    val versionCode: Int = BuildConfig.VERSION_CODE
    val versionName: String = BuildConfig.VERSION_NAME
    val buildType: String = BuildConfig.BUILD_TYPE

    // ---- Build flags ----
    val isDebug: Boolean = BuildConfig.DEBUG
    val isRelease: Boolean = !BuildConfig.DEBUG

    // ---- Derived ----
    val fileProviderAuthority: String = "${BuildConfig.APPLICATION_ID}.provider"
    val versionLabel: String = "v$versionName ($versionCode)"

    val deviceInfo: String
        get() = "Device: ${android.os.Build.MANUFACTURER.replaceFirstChar { it.uppercase() }} ${android.os.Build.MODEL} • Android ${android.os.Build.VERSION.RELEASE} (API ${android.os.Build.VERSION.SDK_INT}) • v$versionName"

    // ---- App-specific fields ----
    val roomDatabaseName: String = BuildConfig.ROOM_DATA_BASE_NAME
}
