# Toastly

A lightweight, Compose-first toast/snackbar system for the SuminoLab platform. Show a toast from
anywhere with a single call, render it with one root-level observer, and get stacking,
auto-dismiss, swipe-to-dismiss, and theme-aware styling for free.

## Setup

Add the module and place one `ToastObserver` at the root of your UI, above the nav host:

```kotlin
setContent {
    AppTheme {
        Box(Modifier.fillMaxSize()) {
            AppNavHost()
            ToastObserver() // binds to the global Toastly controller by default
        }
    }
}
```

## Showing toasts

From anywhere (no injection needed):

```kotlin
Toastly.success("Saved")
Toastly.error("Upload failed")
Toastly.info("Sync started")
Toastly.warning("Battery low")

Toastly.show(
    message = "Item deleted",
    type = ToastType.INFO,
    showButton = true,
    buttonText = "UNDO",
    onButtonClick = { restore() },
)

Toastly.dismissAll() // clears the queue and everything on screen
```

## Testable / injectable usage

Depend on the `ToastController` interface where you want unit tests:

```kotlin
class MyViewModel(private val toasts: ToastController) {
    fun onError() = toasts.error("Something failed")
}
```

The library ships **no** DI dependency. To wire it with Hilt, provide the shared instance from
your own module and bind the same instance into the root `ToastObserver`:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object ToastModule {
    @Provides @Singleton
    fun provideToastController(): DefaultToastController = DefaultToastController()

    @Provides
    fun bindController(impl: DefaultToastController): ToastController = impl
}
```

```kotlin
ToastObserver(controller = hiltViewModel<RootViewModel>().toastController)
```

## Configuration

`ToastConfig` covers everything: `type`, `style` (5 visual treatments), `position` (top/bottom),
`duration` (`Short`, `Long`, `Custom(ms)`, `Indefinite`), icon/button/close visibility, an action
button, an `onDismiss` callback, and `showDelayMs`.

## Behaviour notes

- Toasts are queued in the controller and drained by whichever observer is currently resumed, so a
  toast requested during a screen transition still appears.
- Up to `ToastState.maxStack` toasts are visible at once; older ones fade and scale back.
- `Indefinite` toasts never auto-dismiss — dismiss them via swipe, the close button, or
  `dismissAll()`.
