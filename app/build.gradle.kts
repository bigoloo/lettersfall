plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlinx-serialization")
}

android {

    compileSdk = 33
    defaultConfig {
        applicationId = "com.bigoloo.lettersfall"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packagingOptions {
        resources {
            excludes.add("META-INF/com.android.tools/proguard/coroutines.pro")
        }
    }
    testOptions {
        unitTests.apply {
            isReturnDefaultValues = true
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    namespace = "com.bigoloo.lettersfall"
}
dependencies {

    implementation(libs.kotlinx.serialization)
    implementation(libs.coroutine.android)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.retrofit)
    testImplementation(libs.coroutine.test)
    testImplementation(libs.bundles.junit)

    debugImplementation(libs.leak.canary)

    val composeBom = platform(libs.compose.bom)

    implementation(composeBom)
    implementation(libs.compose.material3)
    implementation(libs.bundles.compose.ui)

    androidTestImplementation(composeBom)
    debugImplementation(libs.compose.ui.test.manifest)
    androidTestImplementation(libs.compose.ui.test.junit4)
}
