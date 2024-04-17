package de.iits.techtalk.kmp.db

import app.cash.sqldelight.db.SqlDriver
import de.iits.techtalk.kmp.Database

expect class DriverFactory() {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): Database {
    val driver = driverFactory.createDriver()
    val database = Database(driver)

    return database
}