package de.iits.techtalk.kmp.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import de.iits.techtalk.kmp.Database

actual class DriverFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:kmp-demo.db")
        Database.Schema.create(driver)
        return driver
    }
}