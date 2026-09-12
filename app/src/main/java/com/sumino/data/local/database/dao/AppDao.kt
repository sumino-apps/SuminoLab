/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.sumino.data.local.database.entity.AppEntity
import kotlinx.coroutines.flow.Flow

/**
 * Universal starter Data Access Object (DAO).
 *
 * Demonstrates best-practice asynchronous Room access:
 * - Read operations return reactive [Flow].
 * - Write/Delete operations are suspend functions executed off the main thread.
 */
@Dao
interface AppDao {

    @Query("SELECT * FROM app_items ORDER BY created_at DESC")
    fun getAllItems(): Flow<List<AppEntity>>

    @Query("SELECT * FROM app_items WHERE id = :id LIMIT 1")
    fun getItemById(id: Long): Flow<AppEntity?>

    @Query("SELECT * FROM app_items WHERE is_favorite = 1 ORDER BY created_at DESC")
    fun getFavoriteItems(): Flow<List<AppEntity>>

    @Upsert
    suspend fun upsert(item: AppEntity): Long

    @Upsert
    suspend fun upsertAll(items: List<AppEntity>)

    @Delete
    suspend fun delete(item: AppEntity)

    @Query("DELETE FROM app_items WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM app_items")
    suspend fun clearAll()
}
