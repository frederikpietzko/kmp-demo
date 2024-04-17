val kotlinx_serialization_version: String by project

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

group = "de.iits.techtalk.kmp"
version = "unspecified"


kotlin {
    js(IR) {
        browser()
    }
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinx_serialization_version")
            }
        }
    }
}
