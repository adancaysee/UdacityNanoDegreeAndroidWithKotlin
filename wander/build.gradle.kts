plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.udacity.wander"
    compileSdk = Config.compileSdkVersion

    defaultConfig {
        applicationId = "com.udacity.wander"
        minSdk = Config.minSdkVersion
        targetSdk = Config.targetSdkVersion
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(Libraries.androidxCore)
    implementation(Libraries.androidxAppcompat)
    implementation(Libraries.material)
    implementation(Libraries.constraintLayout)

    implementation(Libraries.playServicesMaps)

    testImplementation(TestLibraries.junit)
    androidTestImplementation(AndroidTestLibraries.junit)
    androidTestImplementation(AndroidTestLibraries.espressoCore)
}