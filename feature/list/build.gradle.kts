plugins {
    alias(libs.plugins.sample.android.library)
}

android {
    namespace = "com.example.feature.list"
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:util"))
    implementation(project(":data"))

    /* material */
    implementation(libs.bundles.support)
    /* Navigation */
    implementation(libs.navigationFragmentKtx)
    implementation(libs.navigationUiKtx)
    /* Tesing */
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
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
    testImplementation(libs.androidx.archcore.testing)
    androidTestImplementation(libs.androidx.archcore.testing)

}