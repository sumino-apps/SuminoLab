# SuminoLab

A reusable Android platform & starter template — **build once, reuse everywhere**. 
SuminoLab serves as both a **reusable library platform** and a **production-ready app starter template** for Sumino Apps.

---

## What's Inside

1. **`:core:designsystem` (`com.sumino:designsystem`)**
   - Centralized Material 3 Design System (`SuminoLabTheme`, color palettes, spacing, elevation, motion, typography tokens).
   - Debug guidelines and layout visualizers (`CenterLines`, `GridLines`, `DebugBounds`, `SafeAreaOverlay`, `DesignOverlay`).
2. **`:app` (Universal Application Template)**
   - Single-module, **Package-by-Feature** architecture for solo developers.
   - Hilt DI setup (`@HiltAndroidApp`, `AppModule`, `Qualifiers`, `SharedPrefModule`, `DatabaseModule`, `BindsModule`).
   - Local Persistence Foundations:
     - Synchronous `SharedPreferences` + Gson (`SharedPref`, `PrefKeys`).
     - Asynchronous reactive DataStore (`ProtoDataStorePrefs`, `DataStorePrefsSerializer`).
     - Modern Room Database (`AppDatabase`, `AppDao`, `AppEntity`, `Converters`).
   - Clean Domain Layer (`AppModel`, `AppRepository`, `GetAppConfigUseCase`, `ClipboardHelper`).
   - Shared UI Foundations (`AppTopBar`, `NetworkSnackbar`, `CenterAlertDialog`, `AppBottomSheet`, `ModifierExt`, `UiExtensions`).
   - Real-time `NetworkMonitor` with OEM cold-start resilience.
   - Zero-flicker reactive theming (`ThemeManager` + `AppTheme`).
   - Type-safe Jetpack Navigation Compose 2.8+ using Kotlin Serialization.
   - Decoupled `AppConfig` single source of truth.

---

## Requirements

| Tool | Version |
| :--- | :--- |
| **AGP** | `9.3.2` |
| **Kotlin** | `2.4.10` |
| **`compileSdk`** | `37` (minorApiLevel 1) |
| **`minSdk`** | `24` or higher |
| **Java** | `17` |
| **Compose** | Enabled in consuming modules |

---

## Architecture & Guidelines

Complete documentation for the engineering patterns, 5-part feature contract, and agent rules:
- **[ARCHITECTURE.md](file:///d:/SuminoLab/ARCHITECTURE.md)**: Deep dive into the architecture, package structure, and step-by-step tutorial on adding new features.
- **[AGENTS.md](file:///d:/SuminoLab/AGENTS.md)**: Engineering rules and conventions for AI assistants and contributors.

---

## How to Reuse `:core:designsystem` in Other Apps (Composite Build)

The recommended way to reuse `:core:designsystem` across your other apps is a **Gradle composite build** (`includeBuild`).

### Step 1 — Include in `settings.gradle.kts`
```kotlin
// settings.gradle.kts (consuming app)
includeBuild("../SuminoLab") // Relative path to SuminoLab directory
```

### Step 2 — Declare Dependency in App Module
```kotlin
// app/build.gradle.kts (consuming app)
dependencies {
    implementation("com.sumino:designsystem")
}
```

### Step 3 — Use in Jetpack Compose
```kotlin
setContent {
    SuminoLabTheme {
        AppContent()
    }
}
```
Tokens are accessed directly inside composables via `Theme`:
`Theme.colorScheme`, `Theme.spacing`, `Theme.elevation`, `Theme.motion`, `Theme.dimens`.
