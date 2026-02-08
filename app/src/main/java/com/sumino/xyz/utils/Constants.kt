package com.sumino.xyz.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Constants @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        const val SHARED_PREFERENCE_FILE_NAME = "APP-NAME-PREFERENCE"
    }

}