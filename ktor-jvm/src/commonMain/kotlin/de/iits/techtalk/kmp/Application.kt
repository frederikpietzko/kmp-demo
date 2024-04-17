package de.iits.techtalk.kmp

import de.iits.techtalk.kmp.db.getTodoRepository
import de.iits.techtalk.kmp.dto.CreateTodoRequest
import de.iits.techtalk.kmp.dto.TodoDto
import de.iits.techtalk.kmp.model.Todo
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun Application.module() {
    install(IgnoreTrailingSlash)
    install(CORS) {
        anyHost()
        HttpMethod.DefaultMethods.forEach { allowMethod(it) }
        allowHeader(HttpHeaders.AccessControlAllowHeaders)
        allowHeader(HttpHeaders.ContentType)
    }
    install(ContentNegotiation) {
        json(Json {
            isLenient = true
        })
    }
    val todoRepository = getTodoRepository()
    routing {

        route("/todos") {

            get {
                val todos = todoRepository.findAll().map(Todo::toDto)
                call.respond(todos)
            }

            post {
                val req = call.receive<CreateTodoRequest>()
                val todo = todoRepository.create(req.task)
                call.respond(todo.toDto())
            }
        }

        route("/todo/{id}") {

            get {
                val id = call.parameters["id"]?.toInt() ?: throw BadRequestException("Id not Valid.")
                val todo = todoRepository.getById(id) ?: throw NotFoundException()
                call.respond(todo.toDto())
            }

            patch("/done") {
                val id = call.parameters["id"]?.toInt() ?: throw BadRequestException("Id not Valid.")
                var todo = todoRepository.getById(id) ?: throw NotFoundException()
                todo = todoRepository.update(todo.copy(done = !todo.done))
                call.respond(todo.toDto())
            }

            delete {
                val id = call.parameters["id"]?.toInt() ?: throw BadRequestException("Id not Valid.")
                todoRepository.delete(id)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}


fun Todo.toDto() = TodoDto(
    id = id, task = task, done = done
)