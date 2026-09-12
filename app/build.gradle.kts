plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.googleDevtoolsKsp)

    alias(libs.plugins.hiltPlugin)
    alias(libs.plugins.serialization)

    id("kotlin-parcelize")
}

object ThisApp {
    const val APP_PACKAGE_NAME = "com.sumino"
    const val APP_VERSION_CODE = 1
    const val APP_VERSION_NAME = "1.0.0.0" // (Major.Minor.Patch.Design)
}

base {
    archivesName = "Sumino-${ThisApp.APP_VERSION_NAME}"
}

android {
    namespace = ThisApp.APP_PACKAGE_NAME
    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = ThisApp.APP_PACKAGE_NAME
        minSdk = 24
        targetSdk = 36
        versionCode = ThisApp.APP_VERSION_CODE
        versionName = ThisApp.APP_VERSION_NAME
        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        applyBuildConfigFields()
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        jniLibs.useLegacyPackaging = false
        resources {
            excludes.addAll(
                listOf(
                    "/META-INF/{AL2.0,LGPL2.1}",
                    "/META-INF/LICENSE.md",
                    "/META-INF/LICENSE-notice.md",
                    "META-INF/DEPENDENCIES",
                    "META-INF/LICENSE",
                    "META-INF/NOTICE",
                    "META-INF/*.kotlin_module"
                )
            )

            pickFirsts.add("META-INF/io.netty.versions.properties")
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)

        freeCompilerArgs.addAll(
            listOf(
                "-XXLanguage:+PropertyParamAnnotationDefaultTargetMode",
                "-opt-in=kotlin.RequiresOptIn",
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
            )
        )
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    //  Core AndroidX & Desugaring
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.activity.compose)
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    //  Compose (BOM aligned)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.ui.text.google.fonts)
    implementation(libs.material)
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.constraintlayout.compose)

    //  Navigation
    implementation(libs.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    //  Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    //  Local Storage (DataStore Preferences)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.datastore.preferences)

    //  Local Storage (Room Database)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    //  Serialization & JSON
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.gson)

    //  Hilt / DI
    implementation(libs.hilt.android)
    ksp(libs.dagger.compiler)
    ksp(libs.hilt.compiler)

    //  Image Loading
    implementation(libs.coil.compose)

    //  Logging & Diagnostics
    implementation(libs.timber)

    //  Design System module
    implementation(project(":core:designsystem"))

    //  Testing
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    //  Debug
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

fun com.android.build.api.dsl.DefaultConfig.applyBuildConfigFields() {
    val roomDatabaseName = (project.findProperty("ROOM_DATA_BASE_NAME") as String?) ?: "AppDatabase"
    buildConfigField("String", "ROOM_DATA_BASE_NAME", "\"$roomDatabaseName\"")
}