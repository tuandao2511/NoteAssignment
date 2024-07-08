@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.sample.android.application)
}
android {
    namespace = "com.example.noteappassignment"

    defaultConfig {
        applicationId = "com.example.noteappassignment"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:util"))
    implementation(project(":feature:list"))
    implementation(project(":feature:details"))

    testImplementation(libs.junit)

    implementation(libs.bundles.support)
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

    /* lifecycle */
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.navigation)

    /* Test */
    androidTestImplementation(libs.bundles.androidTest)

    /* corountine*/
    implementation(libs.bundles.coroutine)
}