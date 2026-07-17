package com.sumino.toastly.model

/**
 * How long a toast stays visible before it auto-dismisses.
 */
sealed class ToastDuration {
    /** Roughly 2 seconds. */
    data object Short : ToastDuration()

    /** Roughly 4 seconds. */
    data object Long : ToastDuration()

    /** A caller-defined duration in milliseconds. */
    data class Custom(val ms: Long) : ToastDuration()

    /** Never auto-dismisses; stays until dismissed by swipe, close, or `dismissAll()`. */
    data object Indefinite : ToastDuration()
}

/** Duration in milliseconds, or `null` for [ToastDuration.Indefinite] (no auto-dismiss). */
internal fun ToastDuration.toMs(): Long? = when (this) {
    is ToastDuration.Short -> 2_000L
    is ToastDuration.Long -> 4_000L
    is ToastDuration.Custom -> ms
    is ToastDuration.Indefinite -> null
} as Long?
