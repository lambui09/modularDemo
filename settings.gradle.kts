pluginManagement {
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
rootProject.name = "Demo Modular"
include(":app")
include(":core:ui")
include(":core:test")
include(":design-system")
include(":core:domain")
include(":core:network")
include(":core:data")
include(":core:database")
include(":core:datastore")
include(":feature:search")
include(":feature:splash")
include(":feature:login")
