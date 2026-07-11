package com.sumino.xyz.di

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sumino.xyz.core.network.NetworkMonitor
import com.sumino.xyz.data.local.datastore.AppPreferencesSerializer
import com.sumino.xyz.data.local.datastore.DataStoreManager
import com.sumino.xyz.data.utils.EncryptionHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    fun provideResources(@ApplicationContext context: Context): Resources {
        return context.resources
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideNetworkMonitor(
        connectivityManager: ConnectivityManager
    ): NetworkMonitor = NetworkMonitor(connectivityManager)




    @Provides
    @Singleton
    fun provideGson() : Gson = GsonBuilder().setPrettyPrinting() // For better formatting of JSON
        .disableHtmlEscaping() // Avoid escaping HTML entities
        .create()

    @Provides
    @Singleton
    fun provideEncryptionHelper(gson : Gson) : EncryptionHelper {
        return EncryptionHelper(gson)
    }

    @Provides
    @Singleton
    fun provideAppPreferencesSerializer(
        gson : Gson, encryptionHelper : EncryptionHelper
    ) : AppPreferencesSerializer {
        return AppPreferencesSerializer(gson, encryptionHelper)
    }


    @Provides
    @Singleton
    fun provideAppPreferences(
        @ApplicationContext context : Context, appPreferencesSerializer : AppPreferencesSerializer
    ) : DataStoreManager {
        return DataStoreManager(context, appPreferencesSerializer)
    }

}