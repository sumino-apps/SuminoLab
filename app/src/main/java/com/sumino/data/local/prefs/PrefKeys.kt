/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.data.local.prefs

/**
 * Centralized constant keys for [SharedPreferences].
 *
 * Add application-specific preference keys here to prevent typos and ensure
 * single-source-of-truth across the app.
 */
object PrefKeys {

    // First-time onboarding & policy consent
    const val IS_FIRST_TIME_LAUNCH = "isFirstTimeLaunch"
    const val FIRST_LAUNCH_TIME = "firstLaunchTime"
    const val ACCEPT_PRIVACY_POLICY = "accept_privacy_policy"

    // App Preferences
    const val THEME_MODE = "themeMode"
    const val APP_LANGUAGE = "app_language"

    // App Review & Prompts
    const val HAS_USER_REVIEWED = "hasUserReviewed"
    const val LAST_REVIEW_PROMPT_TIME = "last_review_prompt_time"

    // Version Tracking
    const val LAST_KNOWN_VERSION_CODE = "last_known_version_code"
}
