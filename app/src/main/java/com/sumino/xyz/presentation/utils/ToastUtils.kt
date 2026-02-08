package com.sumino.xyz.presentation.utils

import android.content.Context
import android.os.Build
import android.view.Gravity
import android.widget.Toast
import java.lang.ref.WeakReference

object ToastUtils {

    private var activeToast: WeakReference<Toast>? = null

    private fun Context.showInternalToast(
        message: String,
        duration : Int = Toast.LENGTH_SHORT,
        gravity : Int = Gravity.BOTTOM
    ) {
        // cancel previous toast
        activeToast?.get()?.cancel()

        val toast = Toast.makeText(
            applicationContext,
            message,
            duration
        ).apply {

            // Gravity works only below Android 11
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                setGravity(gravity, 0, 0)
            }

            // Clear reference when toast hides (API 30+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                addCallback(object : Toast.Callback() {
                    override fun onToastHidden() {
                        activeToast = null
                    }
                })
            }
        }

        toast.show()
        activeToast = WeakReference(toast)
    }


    fun Context.showToast(
        message: String,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        showInternalToast(message, duration, Gravity.BOTTOM)
    }

    fun Context.showToastLong(message: String) {
        showInternalToast(message, Toast.LENGTH_LONG, Gravity.BOTTOM)
    }

    fun Context.showToastTop(message: String) {
        showInternalToast(message, Toast.LENGTH_SHORT, Gravity.TOP)
    }

    fun Context.showToastCenter(message: String) {
        showInternalToast(message, Toast.LENGTH_SHORT, Gravity.CENTER)
    }

    fun cancel() {
        activeToast?.get()?.cancel()
        activeToast = null
    }
}
