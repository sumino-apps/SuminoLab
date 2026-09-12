# SuminoLab — Agent & Engineering Guidelines

This repository serves as the **Universal Android Template & Reusable Platform** for Sumino Apps.
All AI assistants, contributors, and tools must strictly adhere to the rules and patterns established below.

---

## 1. Project Mindset & Persona
- **Organization:** Sumino Apps (Author: Rohitraj Khorwal).
- **Context:** Solo developer building production-grade, maintainable, scalable Android apps.
- **Philosophy:** "Build once, reuse everywhere." Keep code clear, direct, and self-documenting. Avoid unnecessary complexity or premature abstractions.

---

## 2. Non-Negotiable Rules
1. **NEVER run Gradle build or test commands unasked:** Do NOT run `./gradlew`, `gradlew.bat`, `assemble*`, or tests unless explicitly requested by the user.
2. **Always use the latest non-deprecated APIs:**
   - Compose Navigation 2.8+ with Kotlin Serialization (`@Serializable` route objects, `composable<Route>`).
   - Lifecycle-safe collection: `collectAsStateWithLifecycle()`.
   - Modern Hilt injection (`hiltViewModel()`, `@HiltViewModel`, `@AndroidEntryPoint`).
   - Android 16 / SDK 37 edge-to-edge conventions (`enableEdgeToEdge()`, insets controllers).
3. **Never hardcode styles, colors, or dimensions:** Always consume tokens from `:core:designsystem` via `Theme` or `MaterialTheme.colorScheme`.

---

## 3. Modularization Strategy (Hybrid Approach)
- **Reusable Platform Modules:** Standalone multi-module libraries (e.g. `:core:designsystem`) designed to be consumed by other apps via Gradle Composite Build (`includeBuild`).
- **Application Modules:** The app itself is maintained as a clean, fast single module (`:app`) organized strictly by **Package-by-Feature**. Do NOT split every app screen into separate Gradle modules unless cross-app reusability is proven.

---

## 4. Package-by-Feature Architecture Contract
Every feature inside `com.sumino.feature.<feature_name>` MUST be self-contained and follow this 5-component pattern:

```
com.sumino.feature.<feature_name>/
├── navigation/
│   └── <Feature>Navigation.kt   # @Serializable route object, NavGraphBuilder.<feature>Screen(), NavController.navigateTo<Feature>()
├── state/
│   └── <Feature>UiState.kt      # @Immutable data class representing full UI state
├── <Feature>Route.kt            # Stateful Composable: wires ViewModel, collects StateFlow, passes callbacks
├── <Feature>Screen.kt           # Pure Stateless Composable: renders UI, supports @Preview
└── <Feature>ViewModel.kt        # @HiltViewModel: business logic, exposes immutable StateFlow<UiState>
```

### Key Architectural Guidelines:
- **No ViewModel in Screen Composable:** `<Feature>Screen` must NEVER accept a ViewModel or NavController. It accepts state and emits lambda events.
- **State Hoisting & Stability:** All UI state models must be immutable (`@Immutable data class`).
- **Navigation Decoupling:** Screens communicate with the navigation host via callbacks (`onNavigateToX = { ... }`), never by passing `NavController` into deep composables.

---

## 5. Core Foundation Reference
- **Dependency Injection:** Configured in `com.sumino.di`.
  - Application-level singletons in `AppModule.kt`.
  - Coroutine dispatchers injected via `@IoDispatcher`, `@DefaultDispatcher`, and `@MainDispatcher`.
  - Application scope injected via `@ApplicationScope`.
- **Application Class:** `com.sumino.MyApplication` (`@HiltAndroidApp`).
  - Initializes `ThemeManager` synchronously to eliminate cold-start theme flicker.
  - Plants `Timber.DebugTree` in debug builds with `(FileName:LineNumber)` formatting.
- **System Configuration:** `com.sumino.core.config.AppConfig` is the single source of truth for `BuildConfig` properties.
- **Network Monitoring:** `com.sumino.core.connectivity.NetworkMonitor` exposes real-time `isOnline: Flow<Boolean>` with OEM cold-start handling (Samsung/Xiaomi race condition fix) and `isWifi()` helper.
- **Theming:** `com.sumino.core.theme.AppTheme` wraps `:core:designsystem`'s `SuminoLabTheme` and bridges it with reactive `ThemeManager` (`SYSTEM`, `LIGHT`, `DARK`).

---

## 6. How to Add a New Feature Checklist
1. Create directory `com.sumino.feature.<name>`.
2. Define `@Serializable object <Name>Route` in `navigation/<Name>Navigation.kt`.
3. Create `@Immutable data class <Name>UiState` in `state/<Name>UiState.kt`.
4. Create `<Name>ViewModel : ViewModel()` with `@HiltViewModel`.
5. Create stateless `<Name>Screen` with `@Preview`.
6. Create stateful `<Name>Route` wiring the ViewModel and Screen.
7. Register the screen in `com.sumino.ui.activity.main.MainNavHost.kt`.
