plugins {
    `kotlin-dsl`
}

group = "com.example.plugins"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.tools.build.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("sampleAndroidApplication") {
            id = "sample.android.application"
            implementationClass = "com.example.plugins.AppPlugin"
        }
        register("sampleAndroidLibrary") {
            id = "sample.android.library"
            implementationClass = "com.example.plugins.LibraryPlugin"
        }
    }
}