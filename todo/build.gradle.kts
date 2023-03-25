plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.udacity.todo"
    compileSdk = Config.compileSdkVersion

    defaultConfig {
        applicationId = "com.udacity.todo"
        minSdk = 21
        targetSdk = Config.targetSdkVersion
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
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

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }

}

dependencies {

    //App dependencies
    implementation(Libraries.androidxCore)
    implementation(Libraries.androidxAppcompat)
    implementation(Libraries.material)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.swiperefreshlayout)
    implementation(Libraries.timber)

    //Architecture components
    implementation(Libraries.androidxNavigationUi)
    implementation(Libraries.androidxNavigationFragment)
    implementation(Libraries.androidxRoomRuntime)
    implementation(Libraries.androidxRoomKtx)
    kapt(Libraries.androidxRoomCompiler)

    testImplementation(TestLibraries.junit)
    testImplementation(TestLibraries.androidXJunitKtx)
    testImplementation(TestLibraries.androidXTestCoreKtx)
    testImplementation(TestLibraries.androidXArchCoreTesting)
    testImplementation(TestLibraries.robolectric)
    testImplementation(TestLibraries.truth)
    testImplementation(TestLibraries.coroutine)

    androidTestImplementation(TestLibraries.androidXJunitKtx)
    androidTestImplementation(TestLibraries.androidXArchCoreTesting)
    androidTestImplementation(TestLibraries.espressoCore)
    androidTestImplementation(TestLibraries.espressoContrib)
    androidTestImplementation(TestLibraries.mockitoAndroid)
    androidTestImplementation(TestLibraries.coroutine)
    androidTestImplementation(TestLibraries.truth)

    debugImplementation(TestLibraries.fragmentTesting)
    debugImplementation(TestLibraries.androidXTestCoreKtx)


}

/*
    debugImplementation("androidx.fragment:fragment-ktx:1.5.5")
    debugImplementation("androidx.test:rules:1.5.0")
    debugImplementation("androidx.test:runner:1.5.2")
*/
