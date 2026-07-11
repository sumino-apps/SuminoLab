package com.sumino.xyz.data.local.datastore

import androidx.datastore.core.Serializer
import com.google.gson.Gson
import com.sumino.xyz.data.local.models.AppPreferencesData
import com.sumino.xyz.data.utils.EncryptionHelper
import timber.log.Timber
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 06-01-2026 at 1:45 PM
 */
@Singleton
class AppPreferencesSerializer @Inject constructor(
    private val gson : Gson,
    private val encryptionHelper : EncryptionHelper
) : Serializer<AppPreferencesData> {

    override val defaultValue : AppPreferencesData = AppPreferencesData()

    override suspend fun readFrom(input : InputStream) : AppPreferencesData {
        return try {
            val encryptedString = input.readBytes().decodeToString()
            val json = encryptionHelper.decrypt(encryptedString, String::class.java)
            if (json.isNullOrEmpty()) {
                Timber.e("Decryption returned null or empty string")
                defaultValue
            } else {
                gson.fromJson(json, AppPreferencesData::class.java) ?: defaultValue
            }
        } catch (e : Exception) {
            Timber.e(e, "Error reading preferences")
            defaultValue
        }
    }

    override suspend fun writeTo(t : AppPreferencesData, output : OutputStream) {
        try {
            val json = gson.toJson(t)
            val encryptedString = encryptionHelper.encrypt(json)
            if (!encryptedString.isNullOrEmpty()) {
                output.write(encryptedString.toByteArray())
            } else {
                Timber.e("Encryption failed: output not written")
            }
        } catch (e : Exception) {
            Timber.e(e, "Error writing preferences")
        }
    }
}