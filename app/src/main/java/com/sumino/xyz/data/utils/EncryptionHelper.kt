package com.sumino.xyz.data.utils

import android.util.Base64
import com.google.gson.Gson
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Sumino Apps
 * @author Rohitraj Khorwal
 * Created 06-01-2026 at 1:50 PM
 */
@Singleton
class EncryptionHelper @Inject constructor(private val gson: Gson) {

    /**
     * Encrypt any type of object including primitive types.
     * First, the object is serialized to a JSON string, then encrypted.
     */
    fun <T> encrypt(input: T): String? {
        return try {
            val jsonString = gson.toJson(input)
            Base64.encodeToString(jsonString.toByteArray(), Base64.DEFAULT)
        } catch (e: Exception) {
            Timber.e(e, "Encryption failed")
            null
        }
    }

    /**
     * Decrypt a string and convert it back to the original object, including primitive types.
     * The string is first decrypted, then deserialized back to the object.
     */
    fun <T> decrypt(input: String, clazz: Class<T>): T? {
        return try {
            val decryptedString = String(Base64.decode(input, Base64.DEFAULT))
            gson.fromJson(decryptedString, clazz)
        } catch (e: Exception) {
            Timber.e(e, "Decryption failed")
            null
        }
    }
}