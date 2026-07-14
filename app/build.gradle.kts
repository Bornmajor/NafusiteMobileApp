import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.services)
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.majasociet.nafusitemobileapp"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.majasociet.nafusitemobileapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Load the local.properties file
        val localProperties = Properties();
        val localPropertiesFile = rootProject.file("local.properties");

        if(localPropertiesFile.exists()){
            localProperties.load(
                FileInputStream(localPropertiesFile)
            )
        }

        // Access the properties
        val cloudinaryApiSecret = localProperties.getProperty("CLOUDINARY_API_SECRET") ?: ""
        val cloudinaryApiKey = localProperties.getProperty("CLOUDINARY_API_KEY") ?: ""

        val formattedApiCloudinarySecret = "\"${cloudinaryApiSecret.replace("\"", "\\\"")}\""
        val formattedCloudinaryApiKey = "\"${cloudinaryApiKey.replace("\"", "\\\"")}\""

        // Inject into generated build config
        buildConfigField("String","CLOUDINARY_API_SECRET",formattedApiCloudinarySecret)
        buildConfigField("String","CLOUDINARY_API_KEY",formattedCloudinaryApiKey)


    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {
    detektPlugins(libs.detekt.formatting)

    implementation(libs.timber)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.retrofit2.retrofit)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.coil3.compose)
    implementation(libs.coil3.network.okhttp)
    implementation(libs.androidx.compose.material3.adaptive.navigation.suite)
    implementation(libs.kotlinx.serialization.json)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)

    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.ui.compose.material3)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.compose.spinkit) {
        exclude("com.github.jitpack")
    }

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.mockk)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}