/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.ui.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import timber.log.Timber

/**
 * Finds the nearest [Activity] instance by unwrapping [ContextWrapper]s.
 */
fun Context.findActivity(): Activity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) return currentContext
        currentContext = currentContext.baseContext
    }
    return null
}

/**
 * Displays a short toast message.
 */
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

/**
 * Safely opens an external URL via [Intent.ACTION_VIEW].
 */
fun Context.openUrl(url: String) {
    runCatching {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }.onFailure { e ->
        Timber.e(e, "Failed to open URL: $url")
    }
}

/**
 * Shares plain text via the system share sheet.
 */
fun Context.shareText(text: String, title: String = "Share") {
    runCatching {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, title).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(shareIntent)
    }.onFailure { e ->
        Timber.e(e, "Failed to share text: $text")
    }
}
