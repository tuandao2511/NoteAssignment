plugins {
    alias(libs.plugins.sample.android.library)
}

android {
    namespace = "com.example.core.ui"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.bundles.support)
    implementation(libs.lottie)
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
}