
plugins {
    kotlin("multiplatform") version "1.9.22" apply false
    kotlin("plugin.serialization") version "1.9.22" apply  false
    id("org.jetbrains.compose") version "1.6.2" apply false
    id("app.cash.sqldelight") version "2.0.2" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}