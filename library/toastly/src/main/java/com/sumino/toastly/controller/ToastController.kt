package com.sumino.toastly.controller

import com.sumino.toastly.model.ToastConfig
import com.sumino.toastly.model.ToastDuration
import com.sumino.toastly.model.ToastPosition
import com.sumino.toastly.model.ToastStyle
import com.sumino.toastly.model.ToastType

/**
 * Entry point for showing and dismissing toasts.
 *
 * This is the abstraction that producers (ViewModels, repositories, use cases) should
 * depend on. Depending on the interface — rather than the concrete [DefaultToastController]
 * or the global [com.sumino.toastly.Toastly] object — keeps those callers unit-testable:
 * a test can inject a fake [ToastController] and assert on what was shown.
 *
 * A [com.sumino.toastly.ui.ToastObserver] placed at the root of the UI renders whatever is
 * shown through this controller. Only the abstract members [show] and [dismissAll] carry
 * behaviour; every other function is a convenience wrapper around [show].
 *
 * ### Dependency injection
 * The library intentionally does **not** depend on any DI framework. To use it with Hilt,
 * provide the shared instance from your own module:
 * ```
 * @Module
 * @InstallIn(SingletonComponent::class)
 * object ToastModule {
 *     @Provides
 *     @Singleton
 *     fun provideToastController(): ToastController = DefaultToastController()
 * }
 * ```
 * Bind the same instance into your `ToastObserver` at the UI root.
 */
interface ToastController {

    /**
     * Enqueues [config] for display. The active [com.sumino.toastly.ui.ToastObserver] picks it
     * up and animates it in. Safe to call from any thread.
     */
    fun show(config: ToastConfig)

    /**
     * Clears the pending queue and dismisses every currently visible toast, including
     * [ToastDuration.Indefinite] ones that never auto-dismiss.
     */
    fun dismissAll()

    /**
     * Shows a toast built from individual parameters — a convenience over assembling a
     * [ToastConfig] by hand.
     *
     * @param message text shown to the user; keep it short (rendered on up to two lines).
     * @param type semantic category driving icon and accent colour.
     * @param style visual treatment (see [ToastStyle]).
     * @param position screen edge the toast anchors to.
     * @param duration how long the toast stays visible before auto-dismiss.
     * @param showIcon whether to render the leading type icon.
     * @param showButton whether to render the trailing action button.
     * @param showCloseButton whether to render the trailing close button.
     * @param buttonText label for the action button when [showButton] is `true`.
     * @param onButtonClick invoked when the action button is tapped.
     * @param onDismiss invoked once the toast is dismissed (auto, swipe, or close).
     * @param showDelayMs delay before the toast appears, in milliseconds.
     */
    fun show(
        message: String,
        type: ToastType = ToastType.INFO,
        style: ToastStyle = ToastStyle.DEFAULT,
        position: ToastPosition = ToastPosition.BOTTOM,
        duration: ToastDuration = ToastDuration.Short,
        showIcon: Boolean = true,
        showButton: Boolean = false,
        showCloseButton: Boolean = false,
        buttonText: String = "",
        onButtonClick: (() -> Unit)? = null,
        onDismiss: (() -> Unit)? = null,
        showDelayMs: Long = 0L,
    ) = show(
        ToastConfig(
            message = message,
            type = type,
            style = style,
            position = position,
            duration = duration,
            showIcon = showIcon,
            showButton = showButton,
            showCloseButton = showCloseButton,
            buttonText = buttonText,
            onButtonClick = onButtonClick,
            onDismiss = onDismiss,
            showDelayMs = showDelayMs,
        )
    )

    /** Shows an [ToastType.ERROR] toast. Defaults to a filled style and a long duration. */
    fun error(
        message: String,
        style: ToastStyle = ToastStyle.FILLED_COLOR,
        duration: ToastDuration = ToastDuration.Long,
    ) = show(message = message, type = ToastType.ERROR, style = style, duration = duration)

    /** Shows a [ToastType.SUCCESS] toast. */
    fun success(
        message: String,
        style: ToastStyle = ToastStyle.FILLED_COLOR,
        duration: ToastDuration = ToastDuration.Short,
    ) = show(message = message, type = ToastType.SUCCESS, style = style, duration = duration)

    /** Shows a [ToastType.INFO] toast. */
    fun info(
        message: String,
        style: ToastStyle = ToastStyle.DEFAULT,
        duration: ToastDuration = ToastDuration.Short,
    ) = show(message = message, type = ToastType.INFO, style = style, duration = duration)

    /** Shows a [ToastType.WARNING] toast. */
    fun warning(
        message: String,
        style: ToastStyle = ToastStyle.DEFAULT,
        duration: ToastDuration = ToastDuration.Short,
    ) = show(message = message, type = ToastType.WARNING, style = style, duration = duration)
}
