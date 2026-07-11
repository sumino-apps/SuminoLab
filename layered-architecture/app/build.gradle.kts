plugins {
    alias(libs.plugins.android.application)

    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.googleDevtoolsKsp)
    alias(libs.plugins.hiltPlugin)
    alias(libs.plugins.serialization)

    id("kotlin-parcelize")
}
object ThisApp {
    const val APP_PACKAGE_NAME = "com.sumino.xyz"
    const val APP_VERSION_CODE = 1
    const val APP_VERSION_NAME = "1.0.0" // (Major.Minor.Patch)
}


android {
    namespace = ThisApp.APP_PACKAGE_NAME
    compileSdk = 36

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
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }


        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
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


androidComponents {
    onVariants(selector().all()) { variant ->
        variant.sources.java?.addStaticSourceDirectory(
            "build/generated/ksp/${variant.name}/kotlin"
        )
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

ksp {
    arg("compose-destinations.codeGenPackageName", ThisApp.APP_PACKAGE_NAME)
}

dependencies {

    
    // AndroidX Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.backported.fixes)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)

    // Testing - Unit Tests
    testImplementation(libs.junit)
    testImplementation(libs.androidx.room.testing)


    // Testing - Android Tests
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)


    // Debug Tools
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    
    // Compose BOM & UI
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.ui.text.google.fonts)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.activity.compose)

    
    // Compose Material
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.material)

    
    // Lifecycle Components
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.common.java8)

    
    // Navigation
    implementation(libs.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.compose.destinations.core)
    implementation(libs.compose.destinations.bottom.sheet)
    ksp(libs.compose.destinations.ksp)

    
    // Hilt - Dependency Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    ksp(libs.dagger.compiler)

    
    // Room Database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    
    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    
    // Serialization & Collections
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.datetime)
    implementation(libs.gson)

    
    // DataStore & Proto
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.core)
    implementation(libs.protobuf.javalite)

    
    // WorkManager
    implementation(libs.androidx.work.runtime.ktx)

    
    // Image Loading
    implementation(libs.coil.compose)

    
    // UI Components
    implementation(libs.dotsindicator)

    
    // Logging
    implementation(libs.timber)



}


fun com.android.build.api.dsl.DefaultConfig.applyBuildConfigFields() {
    buildConfigField(
        "String", "MMKV_SECRET", "\"sumino_debug_key\""
    )
}