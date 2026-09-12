import com.android.build.api.dsl.LibraryExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import kotlin.apply

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false

    alias(libs.plugins.googleDevtoolsKsp) apply false

    alias(libs.plugins.hiltPlugin) apply false
    alias(libs.plugins.serialization) apply false

}




// Shared configuration for every Android library module. A module only declares its own
// `namespace` (and its dependencies) — the SDK/Java/Compose setup below is applied here once.
// Guarded by the plugin id so :app (an application) and the empty grouping projects
// (:core, :library) are left untouched.
subprojects {
    plugins.withId("com.android.library") {
        extensions.configure<LibraryExtension> {
            compileSdk {
                version = release(37) {
                    minorApiLevel = 1
                }
            }
            defaultConfig {
                minSdk = 24
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
        // Keep Kotlin's bytecode target aligned with the Java target above (matches :app).
        tasks.withType<KotlinCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_17)
            }
        }
    }

    // Turn on the Compose build feature for any library module that opts into Compose by
    // applying the Compose compiler plugin. No-op for :app (it configures its own extension).
    plugins.withId("org.jetbrains.kotlin.plugin.compose") {
        extensions.findByType<LibraryExtension>()?.apply {
            buildFeatures {
                compose = true
            }
        }
    }
}