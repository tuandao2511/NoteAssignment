package com.example.plugins

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class   LibraryPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("dagger.hilt.android.plugin")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.kapt")
                apply("androidx.navigation.safeargs.kotlin")
            }

            extensions.configure<LibraryExtension> {
                configureAndroid(this)
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
//            dependencies {
//                add("implementation", libs.findLibrary("hiltLib").get())
//                add("kapt", libs.findLibrary("hiltCompiler").get())
//                add("androidTestImplementation", libs.findLibrary("hiltTesting").get())
//                add("kaptAndroidTest", libs.findLibrary("hiltCompiler").get())
//            }
        }
    }
}