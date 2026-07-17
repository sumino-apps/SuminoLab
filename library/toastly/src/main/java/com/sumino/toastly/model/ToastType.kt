package com.sumino.toastly.model

/**
 * Semantic category of a toast. Drives the icon and accent colour.
 */
enum class ToastType {
    /** Neutral, informational message. */
    INFO,

    /** A successful, positive outcome. */
    SUCCESS,

    /** A failure or error the user should notice. */
    ERROR,

    /** A caution that isn't yet an error. */
    WARNING,
}
