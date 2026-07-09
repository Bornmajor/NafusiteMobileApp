import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.plugin.serialization")
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

    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    //Network calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    //Json to Kotlin object mapping
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("io.coil-kt.coil3:coil-compose:3.5.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.5.0")
    implementation("androidx.compose.material3:material3-adaptive-navigation-suite")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    // THIS FOR SECURITY & AUTHENTICATION
    implementation("com.google.firebase:firebase-auth")
    // Realtime Database library
    implementation("com.google.firebase:firebase-database")
    implementation(platform("com.google.firebase:firebase-bom:34.15.0"))
    implementation("androidx.media3:media3-exoplayer:1.10.1")
    implementation("androidx.media3:media3-exoplayer-dash:1.10.1")
    implementation("androidx.media3:media3-ui:1.10.1")
    implementation("androidx.media3:media3-ui-compose-material3:1.10.1")
    implementation("androidx.navigation:navigation-compose:2.9.8")
    implementation("com.github.OCNYang.Compose-SpinKit:library:1.0.5") {
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
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}