package com.sumino.xyz.di

import android.content.Context
import com.sumino.xyz.core.crash.CrashHandler
import com.sumino.xyz.core.crash.CrashReporter
import com.sumino.xyz.core.crash.DefaultCrashReporter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CrashModule {

    @Binds
    @Singleton
    abstract fun bindCrashReporter(
        impl: DefaultCrashReporter
    ): CrashReporter

}


@Module
@InstallIn(SingletonComponent::class)
object CrashHandlerModule {

    @Provides
    @Singleton
    fun provideCrashHandler(
        @ApplicationContext context: Context,
        crashReporter: CrashReporter
    ): CrashHandler {
        return CrashHandler(context, crashReporter)
    }
}
