/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.data.local.datastore

import androidx.datastore.core.Serializer
import com.sumino.data.local.datastore.model.DataStorePrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Serializer for [DataStorePrefs] using Kotlinx Serialization.
 *
 * Implements fault-tolerant decoding with automatic fallback to [defaultValue]
 * if the file is empty or corrupted.
 */
@Singleton
class DataStorePrefsSerializer @Inject constructor() : Serializer<DataStorePrefs> {

    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        encodeDefaults = true
        isLenient = true
        allowTrailingComma = true
    }

    override val defaultValue: DataStorePrefs = DataStorePrefs()

    override suspend fun readFrom(input: InputStream): DataStorePrefs {
        return try {
            val jsonString = input.readBytes().decodeToString()
            if (jsonString.isBlank()) {
                return defaultValue
            }
            json.decodeFromString(DataStorePrefs.serializer(), jsonString)
        } catch (e: Exception) {
            Timber.tag("DataStorePrefs").e(e, "Error reading preferences, falling back to default")
            defaultValue
        }
    }

    override suspend fun writeTo(t: DataStorePrefs, output: OutputStream) {
        val jsonString = json.encodeToString(DataStorePrefs.serializer(), t)
        withContext(Dispatchers.IO) {
            output.write(jsonString.toByteArray(Charsets.UTF_8))
        }
    }
}
