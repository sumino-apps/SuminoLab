/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.domain.usecase

import com.sumino.core.config.AppConfig
import javax.inject.Inject

/**
 * Universal starter UseCase demonstrating clean ViewModel-to-Domain decoupling.
 *
 * Each UseCase represents an explicit, single user or system action (following the Single Responsibility Principle).
 */
class GetAppConfigUseCase @Inject constructor() {

    operator fun invoke(): AppConfig = AppConfig
}
