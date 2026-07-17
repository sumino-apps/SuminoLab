package com.sumino.toastly.model

/**
 * Visual treatment of a toast. The background, border, and text adapt to the current
 * [androidx.compose.material3.MaterialTheme] (light/dark); only the accent comes from the
 * toast [ToastType].
 */
enum class ToastStyle {
    /** Neutral raised surface pill with a subtle border and a coloured icon/button. */
    DEFAULT,

    /** Surface background with a thin coloured border and coloured icon/button/close. */
    OUTLINED,

    /** Light accent tint over the surface with a coloured border. */
    TINTED,

    /** Surface background with a coloured vertical left-accent bar. */
    LEFT_ACCENT,

    /** Fully solid brand-colour fill with white content. */
    FILLED_COLOR,
}
