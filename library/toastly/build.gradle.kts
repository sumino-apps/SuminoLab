plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.sumino.toastly"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Compose BOM keeps every Compose artifact on one compatible version set.
    implementation(platform(libs.androidx.compose.bom))

    // Compose UI — the toast layouts are built entirely in Compose.
    // material3 also brings material-icons-core (used for Icons.Filled.Close).
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.foundation)
    // Full animation artifact: AnimatedVisibility + enter/exit specs used by ToastObserver.
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.compose.ui.tooling.preview)

    // Toastly singleton uses a SharedFlow for the queue and delay() for auto-dismiss.
    implementation(libs.kotlinx.coroutines.android)

    // LocalLifecycleOwner + lifecycle-aware collection: the observer registers itself
    // as active on ON_RESUME and pauses while backgrounded.
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Internal debug logging in the Toastly manager.
    implementation(libs.timber)

    // Renders this module's @Preview functions in debug builds only.
    debugImplementation(libs.androidx.compose.ui.tooling)

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}
