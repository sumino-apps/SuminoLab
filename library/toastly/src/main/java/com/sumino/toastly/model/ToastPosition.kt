package com.sumino.toastly.model

/**
 * Screen edge a toast anchors to. In a stack, the newest toast's position wins for the whole
 * stack.
 */
enum class ToastPosition {
    /** Anchored to the top of the screen, below the status bar. */
    TOP,

    /** Anchored to the bottom of the screen, above the navigation bar. */
    BOTTOM,
}
