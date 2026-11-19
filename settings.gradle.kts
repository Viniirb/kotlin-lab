rootProject.name = "kotlin-lab"

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":core")       // Kotlin Multiplatform: regras de negócio compartilhadas por TODAS as plataformas
include(":lessons")    // JVM: lições, exercícios e o Task Manager no terminal
include(":backend")    // JVM: API REST com Ktor
include(":composeApp") // Compose Multiplatform: UI para Android, Desktop, iOS e Web (Wasm)
