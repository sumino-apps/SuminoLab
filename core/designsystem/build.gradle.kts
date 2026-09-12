plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
}

android {
    // SDK, Java 17, and Compose setup are applied from the root build's `subprojects` block.
    namespace = "com.sumino.designsystem"
}

dependencies {
    // Compose BOM keeps every Compose artifact on one compatible version set.
    api(platform(libs.androidx.compose.bom))

    // Exposed as `api`: any module that uses SuminoLabTheme, the color tokens,
    // or the shared @Preview annotations gets Compose + Material 3 transitively.
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.graphics)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.animation)
    api(libs.androidx.compose.ui.tooling.preview)

    // WindowCompat / core view helpers used by SetSystemBarIcons.
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.ui.text.google.fonts)
    implementation(libs.androidx.compose.material.icons.extended)

    // Renders the @Preview annotations in debug builds only.
    debugImplementation(libs.androidx.compose.ui.tooling)

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}
