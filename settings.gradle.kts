pluginManagement {
    includeBuild("plugins")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "NoteAppAssignment"
include(":app")
include(":core:ui")
include(":core:util")
include(":ui")
include(":util")
include(":data")
include(":feature:list")
include(":feature:details")
include(":domain")
