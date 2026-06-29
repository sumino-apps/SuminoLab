package com.sumino.xyz.core.crash

import android.content.Intent
import javax.inject.Inject
import javax.inject.Singleton
import android.os.Process
import com.sumino.xyz.MyApplication.Companion.isDebug
import com.sumino.xyz.data.local.prefs.CrashGuard
import com.sumino.xyz.ui.feature.crash.CrashActivity
import timber.log.Timber
import kotlin.system.exitProcess

@Singleton
class CrashHandler @Inject constructor(
    private val context: android.content.Context,
    private val crashReporter: CrashReporter
) : Thread.UncaughtExceptionHandler {

    private val guard = CrashGuard(context)

    override fun uncaughtException(thread: Thread, throwable: Throwable) {

        try {
            val crashInfo = """
            |App Crashed!
            |Thread: ${thread.name}
            |Exception: ${throwable::class.java.simpleName}
            |Message: ${throwable.message}
            |StackTrace: ${throwable.stackTraceToString()}
        """.trimMargin()

            Timber.e(crashInfo)


            if (isDebug || !guard.isCrashLoop()) {
                guard.markCrashed()
                crashReporter.report(throwable)
                launchCrashActivity(throwable)
                Thread.sleep(400) // activity launch guarantee
            } else {
                Timber.e("Crash loop detected, skipping CrashActivity")
            }


        } catch (e: Exception) {
            // never crash inside crash handler
        }

        Process.killProcess(Process.myPid())
        exitProcess(10)
    }

    private fun launchCrashActivity(throwable: Throwable) {

        val intent = Intent(context, CrashActivity::class.java).apply {
            putExtra(EXTRA_CRASH_MESSAGE, throwable.message ?: "Unknown error")
            if (isDebug) {
                putExtra(EXTRA_STACK_TRACE, throwable.stackTraceToString())
            }
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)
    }

    companion object {
        const val EXTRA_CRASH_MESSAGE = "crash_message"
        const val EXTRA_STACK_TRACE = "stack_trace"
    }
}
