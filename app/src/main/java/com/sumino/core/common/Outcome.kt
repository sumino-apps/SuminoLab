/*
 * Copyright (c) 2026 Sumino Apps
 * Licensed under the Apache License, Version 2.0
 */
package com.sumino.core.common

/**
 * Universal Result / Outcome monad for representing asynchronous or fallible operations.
 *
 * Provides three mutually exclusive states:
 * - [Success]: Holds valid operation output data.
 * - [Error]: Captures a [throwable] or an optional user-friendly [message].
 * - [Loading]: Represents an ongoing operation (ideal for UI loading spinners).
 */
sealed interface Outcome<out T> {

    data class Success<out T>(val data: T) : Outcome<T>

    data class Error(
        val throwable: Throwable? = null,
        val message: String? = null
    ) : Outcome<Nothing>

    data object Loading : Outcome<Nothing>
}

/**
 * Executes [action] when this outcome is [Outcome.Success].
 */
inline fun <T> Outcome<T>.onSuccess(action: (T) -> Unit): Outcome<T> {
    if (this is Outcome.Success) {
        action(data)
    }
    return this
}

/**
 * Executes [action] when this outcome is [Outcome.Error].
 */
inline fun <T> Outcome<T>.onError(action: (Outcome.Error) -> Unit): Outcome<T> {
    if (this is Outcome.Error) {
        action(this)
    }
    return this
}

/**
 * Returns data if [Outcome.Success], otherwise null.
 */
fun <T> Outcome<T>.getOrNull(): T? = (this as? Outcome.Success)?.data

/**
 * Transforms the data held in [Outcome.Success] via [transform].
 */
inline fun <T, R> Outcome<T>.map(transform: (T) -> R): Outcome<R> = when (this) {
    is Outcome.Success -> Outcome.Success(transform(data))
    is Outcome.Error -> this
    is Outcome.Loading -> Outcome.Loading
}
