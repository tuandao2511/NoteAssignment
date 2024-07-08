plugins {
    alias(libs.plugins.sample.android.library)
}

android {
    namespace = "com.example.core.util"
}


java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    /* Hilt */
    implementation(libs.hiltLib)
    implementation(libs.hiltNavLib)
    kapt(libs.hiltCompiler)
    /* For instrumentation tests*/
    androidTestImplementation(libs.hiltTesting)
    kaptAndroidTest(libs.hiltCompiler)
    /* For local unit tests */
    testImplementation(libs.hiltTesting)
    kaptTest(libs.hiltCompiler)

    /* Coroutine */
    implementation(libs.bundles.coroutine)
}