/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.sumino.data.local.prefs.SharedPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing [SharedPreferences] and [SharedPref].
 */
@Module
@InstallIn(SingletonComponent::class)
object SharedPrefModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences = context.getSharedPreferences(
        SharedPref.PREF_FILE_NAME,
        Context.MODE_PRIVATE
    )

    @Provides
    @Singleton
    fun provideSharedPref(
        preferences: SharedPreferences,
        gson: Gson,
        @ApplicationContext context: Context
    ): SharedPref = SharedPref(
        preferences = preferences,
        gson = gson,
        context = context
    )
}
