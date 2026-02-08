package com.sumino.xyz

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.sumino.xyz.core.crash.CrashHandler
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

/**
 * It initializes application-wide resources and configurations.
 *
 * ## Sumino Apps
 *
 * Project Created on: 01 JAN, 2026
 * @author Rohitraj Khorwal
 * @since v1.0.0
 * @see <a href="mailto:sumitra.devapps@gmail.com">sumitra.devapps@gmail.com</a>
 */
@HiltAndroidApp
class MyApplication : Application(), Application.ActivityLifecycleCallbacks,
    DefaultLifecycleObserver {

    companion object {
        val isDebug: Boolean by lazy { BuildConfig.DEBUG }
    }

    @Inject
    lateinit var crashHandler: CrashHandler


    /**
     * Called when the application is starting, before any activity, service, or receiver objects have been created.
     */
    override fun onCreate() {

        super<Application>.onCreate()

        // Registers activity lifecycle callbacks
        this.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)


        if (isDebug) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                StrictMode.setVmPolicy(
                    StrictMode.VmPolicy.Builder().detectAll().penaltyLog()
                        .detectUnsafeIntentLaunch().build()
                )
            }
            // Only in debug builds

            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return "(${element.fileName}:${element.lineNumber})"
                }

                override fun log(
                    priority: Int, tag: String?, message: String, t: Throwable?
                ) {
                    val enhancedMessage = "[${getString(R.string.app_name)}]--->  $message"
                    super.log(priority, tag, enhancedMessage, t)
                }
            })
        }


        Timber.tag("MyApp").d("onCreate Application")

        Thread.setDefaultUncaughtExceptionHandler(crashHandler)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        Timber.tag("MyApp").d("onConfigurationChanged: ")
        super.onConfigurationChanged(newConfig)
    }

    /**
     * Stops logging for tooLarge Exceptions, unregisters activity lifecycle callbacks,
     * and terminates the application.
     */
    override fun onTerminate() {
        Timber.tag("MyApp").d("onTerminate: ")

        ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
        this.unregisterActivityLifecycleCallbacks(this)
        super.onTerminate()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Timber.tag("MyApp").d("onDestroy(owner: LifecycleOwner)")
        super.onDestroy(owner)
    }

    override fun onStart(owner: LifecycleOwner) {
        Timber.tag("MyApp").d("onStart(owner: LifecycleOwner)")
        super.onStart(owner)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Timber.tag("MyApp").d("onStop(owner: LifecycleOwner)")
    }

    /**
     * Called when the activity is created.
     *
     * This method is part of the Activity lifecycle callback in the application,
     * and it is triggered whenever any activity in the app is created.
     */
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Timber.tag("MyApp").d("onActivityCreated: Application Level")

        /* Forces the screen orientation to portrait mode for all versions except Android Oreo (API 26)
        * Crash In API 26
        */

//        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
//        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//        }

    }

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}

}