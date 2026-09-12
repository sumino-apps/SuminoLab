/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.sumino.core.config.AppConfig
import com.sumino.core.theme.ThemeManager
import com.sumino.core.theme.ThemeMode
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Base Application class for the template.
 *
 * Annotating with [@HiltAndroidApp] triggers Hilt's code generation, including a base class
 * that serves as the application-level dependency container.
 */
@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize in-memory theme mode synchronously (defaults to system)
        ThemeManager.init(ThemeMode.SYSTEM)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        if (AppConfig.isDebug) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return "(${element.fileName}:${element.lineNumber})"
                }

                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(priority, tag, "[Sumino] $message", t)
                }
            })
        }

        Timber.d("MyApplication initialized successfully")
    }
}
