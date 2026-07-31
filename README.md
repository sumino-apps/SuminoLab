# SuminoLab

A reusable Android platform — **build once, reuse everywhere**. SuminoLab hosts shared,
production-grade library modules that any Sumino app can consume without copying code.

The recommended way to reuse these modules across your other apps is a **Gradle composite
build** (`includeBuild`). Your app points at this repository on disk and depends on its modules
directly — one source of truth, live edits, no publishing, no duplication.

---

## Available modules

| Module            | Maven coordinate          | What it gives you                                                        |
| ----------------- | ------------------------- | ------------------------------------------------------------------------ |
| `:library:toastly`   | `com.sumino:toastly`      | A Compose toast/notification system — global facade + root observer.     |
| `:core:designsystem` | `com.sumino:designsystem` | The shared theme (`SuminoLabTheme`) and design tokens (color, spacing…). |

- **Group:** `com.sumino`
- **Version:** `1.0.0`

Both modules are **Jetpack Compose** libraries, so the consuming module must have Compose enabled.

---

## Requirements

The consuming project should be on a toolchain compatible with SuminoLab:

| Tool           | Version           |
| -------------- | ----------------- |
| AGP            | `9.3.0`           |
| Kotlin         | `2.2.10`          |
| `compileSdk`   | `36`              |
| `minSdk`       | `24` or higher    |
| Java           | `11`              |
| Compose        | Enabled in the consuming module |

> A composite build compiles SuminoLab's source **as part of your app's build**, so both builds
> must agree on AGP/Kotlin. Keeping the consuming app on the same versions avoids surprises.

---

## How to use it in another project (composite build)

### Step 1 — Put SuminoLab on disk next to your app

Clone (or keep) this repository somewhere your app can reach by a file path. A common layout is
side-by-side:

```
Projects/
├── SuminoLab/     ← this repo
└── MyOtherApp/    ← the app that wants to reuse it
```

```bash
git clone https://github.com/sumino-apps/SuminoLab.git
```

### Step 2 — Include the build in your app's `settings.gradle.kts`

Add `includeBuild(...)` pointing at the SuminoLab folder. Adjust the path to match your layout.

```kotlin
// settings.gradle.kts (consuming app)

includeBuild("../SuminoLab") // relative to this app; an absolute path like "D:/SuminoLab" also works
```

Nothing else in `settings.gradle.kts` needs to change — your existing
`dependencyResolutionManagement { }` and repositories stay as they are.

### Step 3 — Declare the dependencies in your app module

Reference the modules by their Maven coordinates. Gradle **substitutes** each coordinate with the
matching local project from the included build (it matches on `group` + module name, so the
version is irrelevant here).

```kotlin
// app/build.gradle.kts (consuming app)

dependencies {
    implementation("com.sumino:toastly")
    implementation("com.sumino:designsystem")
}
```

### Step 4 — Make sure Compose is enabled in the consuming module

Both modules expose `@Composable` APIs, so the module that calls them needs the Compose compiler
plugin and `buildFeatures.compose = true`:

```kotlin
// app/build.gradle.kts (consuming app)

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose) // Compose compiler
}

android {
    buildFeatures {
        compose = true
    }
}
```

### Step 5 — Sync

Sync Gradle in Android Studio. The SuminoLab modules now compile straight from source into your
app. Done — no AAR, no repository, no token.

---

## Using the modules in code

### Design system — `SuminoLabTheme`

Wrap your app content in the shared theme. Every token group is a parameter with a sensible
default, so override only what a given app needs and inherit the rest.

```kotlin
setContent {
    SuminoLabTheme {
        AppContent()
    }
}

// Partial override example:
SuminoLabTheme(
    lightColors = LightColorScheme.copy(primary = BrandBlue),
    spacing = Spacing(medium = 20.dp),
) {
    AppContent()
}
```

Read tokens inside composables via `Theme` — `Theme.colorScheme`, `Theme.spacing`,
`Theme.elevation`, `Theme.motion`, `Theme.dimens`.

### Toastly — global toasts

Place a single `ToastObserver()` at the root of your UI, above the nav host. Then show toasts
from anywhere through the `Toastly` facade.

```kotlin
setContent {
    SuminoLabTheme {
        Box(Modifier.fillMaxSize()) {
            AppNavHost()
            ToastObserver() // binds to Toastly.controller by default
        }
    }
}

// From anywhere (e.g. a click handler):
Toastly.success("Saved")
Toastly.error("Something failed")
Toastly.show("Heads up", type = ToastType.WARNING)
```

For testable code, inject a `ToastController` into your ViewModels instead of calling the global
object directly (see the KDoc on `ToastController` for a Hilt example). Bind the same
`DefaultToastController` instance into your `ToastObserver` so injected callers drive the same UI.

---

## How updates flow

Because the app depends on SuminoLab's **source** (not a published artifact), any change you make
in SuminoLab is picked up on your app's next build — no re-publish step. Edit a token in
`:core:designsystem`, rebuild the app, and the change is there. This is the main advantage of the
composite build for active co-development.

---

## Troubleshooting

**`Could not find com.sumino:toastly` / the coordinate isn't substituted**
Gradle matches the included build's project coordinates against the requested `group:name`. Make
sure the SuminoLab modules still declare `group = "com.sumino"` (they do, in each module's
`build.gradle.kts`). If a match still isn't found, add an explicit substitution rule:

```kotlin
// settings.gradle.kts (consuming app)

includeBuild("../SuminoLab") {
    dependencySubstitution {
        substitute(module("com.sumino:toastly")).using(project(":library:toastly"))
        substitute(module("com.sumino:designsystem")).using(project(":core:designsystem"))
    }
}
```

**`includeBuild` path not found**
The path is relative to the consuming app's root. Confirm the folder location; use an absolute
path (e.g. `"D:/SuminoLab"`) if the projects are not side-by-side.

**AGP / Kotlin version errors during sync**
The composite build compiles SuminoLab with the app's build. Align the consuming app to the
versions in the [Requirements](#requirements) table.

**`@Composable invocations can only happen from…` / unresolved Compose APIs**
The consuming module is missing Compose. Apply the Compose compiler plugin and set
`buildFeatures.compose = true` (Step 4).

---

## Alternatives (not required for local use)

The composite build is the recommended path for local development. Two other options exist if you
outgrow it:

- **`mavenLocal()`** — publish an AAR to your machine's `~/.m2` with
  `./gradlew :library:toastly:publishToMavenLocal`, then depend on
  `com.sumino:toastly:1.0.0` from an app that lists `mavenLocal()`. A binary, decoupled option,
  but you must re-publish after every change.
- **GitHub Packages** — publish versioned artifacts to this repo's private package registry for
  use across different machines, teammates, or CI. Needed once the library is shared beyond your
  own machine; requires a GitHub token to publish and to consume.

A composite build only works when SuminoLab exists on the same machine at a known path. For a
different machine or CI, switch to one of the published options above.
