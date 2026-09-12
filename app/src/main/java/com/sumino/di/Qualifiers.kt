/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.di

import javax.inject.Qualifier

/**
 * Qualifier for an application-wide [kotlinx.coroutines.CoroutineScope]
 * tied to the application lifecycle that survives configuration changes.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApplicationScope

/**
 * Qualifier for injecting [kotlinx.coroutines.Dispatchers.IO] for disk and network I/O operations.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

/**
 * Qualifier for injecting [kotlinx.coroutines.Dispatchers.Default] for CPU-intensive work.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher

/**
 * Qualifier for injecting [kotlinx.coroutines.Dispatchers.Main] for UI thread operations.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher
