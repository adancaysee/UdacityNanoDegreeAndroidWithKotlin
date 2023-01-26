plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.udacity.gdgfinder"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.udacity.gdgfinder"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(Libraries.androidxCore)
    implementation(Libraries.androidxAppcompat)
    implementation(Libraries.material)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.androidxNavigationUi)
    implementation(Libraries.androidxNavigationFragment)
}