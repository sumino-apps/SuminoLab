/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.data.repository

import com.sumino.core.common.Outcome
import com.sumino.data.local.database.dao.AppDao
import com.sumino.data.local.datastore.ProtoDataStorePrefs
import com.sumino.data.local.datastore.model.DataStorePrefs
import com.sumino.data.local.prefs.SharedPref
import com.sumino.data.mapper.toDomain
import com.sumino.data.mapper.toEntity
import com.sumino.di.IoDispatcher
import com.sumino.domain.model.AppModel
import com.sumino.domain.repository.AppRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Production implementation of [AppRepository].
 *
 * Coordinates data across:
 * - Room database ([AppDao]) for structured persistent entities.
 * - DataStore ([ProtoDataStorePrefs]) for reactive settings and app state.
 * - SharedPreferences ([SharedPref]) for synchronous early-boot configuration.
 */
@Singleton
class AppRepositoryImpl @Inject constructor(
    private val appDao: AppDao,
    private val protoDataStorePrefs: ProtoDataStorePrefs,
    private val sharedPref: SharedPref,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AppRepository {

    override fun getItems(): Flow<List<AppModel>> =
        appDao.getAllItems().map { it.toDomain() }

    override fun getItemById(id: Long): Flow<AppModel?> =
        appDao.getItemById(id).map { it?.toDomain() }

    override fun getFavoriteItems(): Flow<List<AppModel>> =
        appDao.getFavoriteItems().map { it.toDomain() }

    override suspend fun saveItem(item: AppModel): Outcome<Long> = withContext(ioDispatcher) {
        runCatching {
            val insertedId = appDao.upsert(item.toEntity())
            Outcome.Success(insertedId)
        }.getOrElse { e ->
            Timber.e(e, "Failed to save item: $item")
            Outcome.Error(throwable = e, message = e.localizedMessage)
        }
    }

    override suspend fun deleteItem(id: Long): Outcome<Unit> = withContext(ioDispatcher) {
        runCatching {
            appDao.deleteById(id)
            Outcome.Success(Unit)
        }.getOrElse { e ->
            Timber.e(e, "Failed to delete item with id: $id")
            Outcome.Error(throwable = e, message = e.localizedMessage)
        }
    }

    override fun getPreferences(): Flow<DataStorePrefs> =
        protoDataStorePrefs.preferencesFlow

    override suspend fun updatePreferences(update: DataStorePrefs.() -> DataStorePrefs) =
        withContext(ioDispatcher) {
            protoDataStorePrefs.updatePreference(update)
        }
}
