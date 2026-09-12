/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.domain.model

import androidx.compose.runtime.Immutable

/**
 * Pure domain model representing an application entity.
 *
 * Designed according to Clean Architecture principles:
 * - Free of database annotations (e.g. Room @Entity, @PrimaryKey).
 * - Marked with [@Immutable] to optimize Compose recompositions.
 * - Used across Domain, ViewModel, and UI layers.
 */
@Immutable
data class AppModel(
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false
)
