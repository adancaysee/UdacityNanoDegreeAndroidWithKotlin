
object Libraries {
    const val androidxCore = "androidx.core:core-ktx:1.9.0"
    const val androidxAppcompat = "androidx.appcompat:appcompat:1.5.1"
    const val material = "com.google.android.material:material:1.7.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"

    //Lifecycle and live data
    const val lifecycleLivedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:2.5.1"

    //Navigation
    const val androidxNavigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navVersion}"
    const val androidxNavigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navVersion}"

    //Room
    const val androidxRoomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val androidxRoomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val androidxRoomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"

    //Timber
    const val timber = "com.jakewharton.timber:timber:5.0.1"

}

object TestLibraries {
    const val junit = "junit:junit:4.13.2"
}

object AndroidTestLibraries {
    const val junit = "androidx.test.ext:junit:1.1.4"
    const val espressoCore = "androidx.test.espresso:espresso-core:3.5.0"
    const val room = "androidx.room:room-testing:${Versions.roomVersion}"
    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutineVersion}"
}

