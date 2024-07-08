package com.example.plugins

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

class AppPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("dagger.hilt.android.plugin")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.kapt")
                apply("kotlin-kapt")
                apply("androidx.navigation.safeargs.kotlin")
            }

            extensions.configure<BaseAppModuleExtension> {
                configureAndroid(commonExtension = this)
            }

            val kaptExtension = extensions.getByType<KaptExtension>()
            kaptExtension.apply {
                correctErrorTypes = true
            }
        }
    }
}