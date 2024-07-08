@Suppress("DSL_SCOPE_VIOLATION") // Remove when fixed https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(libs.plugins.sample.android.library)
}

android {
    namespace = "com.example.data"
}

dependencies {
    implementation(project(":core:util"))

    /* room */
    implementation(libs.roomKtx)
    implementation(libs.roomRuntime)
    androidTestImplementation(libs.roomTesting)
    kapt(libs.roomCompiler)

    implementation(libs.bundles.coroutine)
    implementation(libs.bundles.androidTest)
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
    /* MockK */
    testImplementation(libs.bundles.mockKTest)
}