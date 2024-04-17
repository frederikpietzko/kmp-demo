package de.iits.techtalk.kmp.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import de.iits.techtalk.kmp.Database

actual class DriverFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(Database.Schema, "kmp-demo.db")
    }
}