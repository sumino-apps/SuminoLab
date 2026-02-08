package com.sumino.xyz.data.local.prefs

import android.content.Context
import androidx.core.content.edit

/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 07-01-2026 at 10:02 PM
 */
class CrashGuard(context: Context) {

    private val prefs = context.getSharedPreferences(
        "crash_guard_prefs",
        Context.MODE_PRIVATE
    )

    fun isCrashLoop(thresholdMs: Long = 8_000L): Boolean {
        val lastCrashTime = prefs.getLong(KEY_LAST_CRASH_TIME, 0L)
        val now = System.currentTimeMillis()
        return (now - lastCrashTime) < thresholdMs
    }

    fun markCrashed() {
        prefs.edit {
            putLong(KEY_LAST_CRASH_TIME, System.currentTimeMillis())
        }
    }

    companion object {
        private const val KEY_LAST_CRASH_TIME = "last_crash_time"
    }
}
