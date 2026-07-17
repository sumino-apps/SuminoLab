package com.sumino.toastly

import com.sumino.toastly.controller.DefaultToastController
import com.sumino.toastly.controller.ToastController
import com.sumino.toastly.model.ToastConfig

/**
 * Global, zero-setup entry point for showing toasts.
 *
 * `Toastly` is a thin facade over a process-wide [DefaultToastController]. It exists so callers
 * that don't use dependency injection can show a toast from anywhere:
 * ```
 * Toastly.show("Saved", ToastType.SUCCESS)
 * Toastly.error("Something failed")
 * ```
 *
 * Render toasts by placing a single observer at the root of your UI, above the nav host:
 * ```
 * setContent {
 *     AppTheme {
 *         Box(Modifier.fillMaxSize()) {
 *             AppNavHost()
 *             ToastObserver() // binds to Toastly.controller by default
 *         }
 *     }
 * }
 * ```
 *
 * Prefer injecting a [ToastController] into ViewModels for testability; reach for this global
 * only from places where injection is impractical. Both drive the same UI when the observer is
 * bound to [controller].
 */
object Toastly : ToastController {

    /**
     * The shared controller instance the default [com.sumino.toastly.ui.ToastObserver] binds to.
     * Expose this to your DI graph if you want injected [ToastController]s to feed the same
     * on-screen observer.
     */
    val controller: DefaultToastController = DefaultToastController()

    override fun show(config: ToastConfig) = controller.show(config)

    override fun dismissAll() = controller.dismissAll()
}
