/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.data.mapper

import com.sumino.data.local.database.entity.AppEntity
import com.sumino.domain.model.AppModel

/**
 * Extension functions for mapping between Data Entities ([AppEntity])
 * and Domain Models ([AppModel]).
 */

fun AppEntity.toDomain(): AppModel = AppModel(
    id = id,
    title = title,
    description = description,
    createdAt = createdAt,
    isFavorite = isFavorite
)

fun AppModel.toEntity(): AppEntity = AppEntity(
    id = id,
    title = title,
    description = description,
    createdAt = createdAt,
    isFavorite = isFavorite
)

fun List<AppEntity>.toDomain(): List<AppModel> = map { it.toDomain() }

fun List<AppModel>.toEntity(): List<AppEntity> = map { it.toEntity() }
