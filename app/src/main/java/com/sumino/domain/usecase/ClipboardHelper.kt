/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.domain.usecase

import android.content.ClipData
import android.content.ClipboardManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Universal clipboard manager for copying, reading, and verifying clipboard contents.
 *
 * Injected as a Singleton across ViewModels and features.
 */
@Singleton
class ClipboardHelper @Inject constructor(
    private val clipboardManager: ClipboardManager
) {

    /**
     * Copies plain text to the system clipboard and executes an optional callback.
     */
    fun copyToClipboard(text: String?, label: String = "COPIED", callback: (() -> Unit)? = null) {
        if (text.isNullOrEmpty()) return
        val clip = ClipData.newPlainText(label, text)
        clipboardManager.setPrimaryClip(clip)
        callback?.invoke()
    }

    /**
     * Retrieves the text currently residing in the primary clipboard clip.
     */
    fun getCopiedText(): String? {
        val clipData = clipboardManager.primaryClip
        if (clipData != null && clipData.itemCount > 0) {
            return clipData.getItemAt(0).text?.toString()
        }
        return null
    }

    /**
     * Checks if the clipboard currently contains text.
     */
    fun hasText(): Boolean {
        val clipData = clipboardManager.primaryClip
        return clipData != null && clipData.itemCount > 0 && !clipData.getItemAt(0).text.isNullOrEmpty()
    }
}
