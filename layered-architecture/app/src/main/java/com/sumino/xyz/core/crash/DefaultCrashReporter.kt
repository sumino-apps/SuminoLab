package com.sumino.xyz.core.crash

import android.util.Log
import timber.log.Timber
import javax.inject.Inject


class DefaultCrashReporter @Inject constructor() : CrashReporter {

    override fun report(throwable: Throwable) {
        try {
            Timber.tag("GlobalCrash").e(throwable, "App crashed")
//            FirebaseCrashlytics.getInstance().apply {
//                recordException(throwable)
//                sendUnsentReports()
//            }
        } catch (e: Exception) {
            Timber.tag("CrashReporter").e(e, "Failed to report crash")
        }

    }
}