val kotlinx_serialization_version: String by project

plugins {
    application
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
    macosArm64()
    macosX64()
    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
            }
        }
    }

}
