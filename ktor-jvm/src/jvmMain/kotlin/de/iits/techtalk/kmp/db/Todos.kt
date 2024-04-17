package de.iits.techtalk.kmp.db

import org.jetbrains.exposed.dao.id.IntIdTable

object Todos : IntIdTable() {
    val task = text("task")
    val done = bool("done")
}