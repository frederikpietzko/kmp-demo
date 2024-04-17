
val ktor_version: String by project
val logback_version: String by project
val exposed_version: String by project
val postgres_version: String by project
val hikari_version: String by project


plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

group = "de.iits.techtalk.kmp"
version = "1.0.0"


kotlin {
    jvm()

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
        }

        val jvmMain by getting {
            dependencies {
                implementation("ch.qos.logback:logback-classic:$logback_version")

                implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
                implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
                implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
                implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")
                implementation("org.postgresql:postgresql:$postgres_version")
                implementation("com.zaxxer:HikariCP:$hikari_version")
            }
        }
    }
}

