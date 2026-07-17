package com.sumino.toastly.model

/**
 * Immutable description of a single toast.
 *
 * Build one directly and pass it to [com.sumino.toastly.controller.ToastController.show], or use
 * the parameterised `show(...)` / typed helpers which assemble it for you.
 *
 * @property message text shown to the user; rendered on up to two lines then ellipsized.
 * @property type semantic category driving the icon and accent colour.
 * @property style visual treatment (see [ToastStyle]).
 * @property position screen edge the toast anchors to.
 * @property duration how long the toast stays visible before auto-dismissing.
 * @property showIcon whether to render the leading type icon.
 * @property showButton whether to render the trailing action button.
 * @property showCloseButton whether to render the trailing close button.
 * @property buttonText label for the action button; the button is hidden when this is blank.
 * @property onButtonClick invoked when the action button is tapped.
 * @property onDismiss invoked once, after the toast has been dismissed (timer, swipe, or close).
 * @property showDelayMs delay before the toast appears, in milliseconds.
 */
data class ToastConfig(
    val message: String,
    val type: ToastType = ToastType.INFO,
    val style: ToastStyle = ToastStyle.DEFAULT,
    val position: ToastPosition = ToastPosition.BOTTOM,
    val duration: ToastDuration = ToastDuration.Short,

    // Visibility controls
    val showIcon: Boolean = true,
    val showButton: Boolean = false,
    val showCloseButton: Boolean = false,

    // Action button label + click
    val buttonText: String = "",
    val onButtonClick: (() -> Unit)? = null,

    // Dismiss callback
    val onDismiss: (() -> Unit)? = null,

    // Delay before the toast appears, in milliseconds
    val showDelayMs: Long = 0L,
)
