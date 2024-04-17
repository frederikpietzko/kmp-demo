package de.iits.techtalk.kmp

import de.iits.techtalk.kmp.db.DriverFactory
import de.iits.techtalk.kmp.db.createDatabase
import de.iits.techtalk.kmp.dto.TodoDto
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun Application.module() {
    install(IgnoreTrailingSlash)
    install(CORS)
    install(ContentNegotiation) {
        json(Json {
            isLenient = true
        })
    }
    val database = createDatabase(DriverFactory())
    routing {
        get("/") {
            call.respondText("Hello World")
        }
        get("/todos") {
            val todos = database
                .todoQueries
                .selectAll()
                .executeAsList()
                .map {
                    TodoDto(
                        id = it.id,
                        task = it.task,
                        done = it.done
                    )
                }
            call.respond(todos)
        }
    }
}
