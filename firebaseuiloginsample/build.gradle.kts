plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.udacity.firebaseuiloginsample"
    compileSdk = Config.compileSdkVersion

    defaultConfig {
        applicationId = "com.udacity.firebaseuiloginsample"
        minSdk = 23
        targetSdk = Config.targetSdkVersion
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        dataBinding = true
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

    implementation(platform(Libraries.firebaseBom))
    implementation(Libraries.firebaseAuthKtx)

    testImplementation(TestLibraries.junit)
    androidTestImplementation(AndroidTestLibraries.junit)
    androidTestImplementation(AndroidTestLibraries.espressoCore)
}