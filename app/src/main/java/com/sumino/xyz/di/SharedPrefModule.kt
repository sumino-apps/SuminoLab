package com.sumino.xyz.di

import android.content.Context
import android.content.SharedPreferences
import com.sumino.xyz.data.local.prefs.SharedPref
import com.sumino.xyz.core.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return  context.getSharedPreferences(
            Constants.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE
        )
    }

    /**
     * Provides SharedPref instance.
     */
    @Provides
    @Singleton
    fun provideSharedPref(
        preferences: SharedPreferences, @ApplicationContext context: Context
    ): SharedPref {
        return SharedPref(preferences,  context)
    }

}