val ktor_version: String by project
val logback_version: String by project
val sqldelight_version: String by project
val pg_driver_version: String by project


plugins {
    application
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("app.cash.sqldelight")
}

group = "de.iits.techtalk.kmp"
version = "1.0.0"

sqldelight {
    databases.register("Database") {
        packageName.set("de.iits.techtalk.kmp")
    }
    linkSqlite = false
}


kotlin {
    jvm()

    val hostOs = System.getProperty("os.name")
    val arch = System.getProperty("os.arch")
    val nativeTarget = when {
        hostOs == "Mac OS X" && arch == "x86_64" -> macosX64("native")
        hostOs == "Mac OS X" && arch == "aarch64" -> macosArm64("native")
        hostOs == "Linux" -> linuxX64("native")
        else -> throw GradleException("Host OS is not supported!")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-core:$ktor_version")
                implementation("io.ktor:ktor-server-cio:$ktor_version")
                implementation("io.ktor:ktor-server-cors:$ktor_version")

                implementation(project(":common"))
                implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
            }
            val nativeMain by getting {
                dependencies {
                    implementation("app.cash.sqldelight:native-driver:$sqldelight_version")
                }
            }
            val jvmMain by getting {
                dependencies {
                    implementation("ch.qos.logback:logback-classic:$logback_version")
                    implementation("app.cash.sqldelight:sqlite-driver:$sqldelight_version")
                }
            }
            val nativeTest by getting {
                dependencies {
                    implementation(kotlin("test"))
                    implementation("io.ktor:ktor-server-test-host:$ktor_version")
                }
            }
        }
    }
}
