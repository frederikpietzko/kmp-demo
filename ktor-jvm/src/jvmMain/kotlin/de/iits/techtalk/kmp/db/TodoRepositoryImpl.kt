package de.iits.techtalk.kmp.db

import de.iits.techtalk.kmp.model.Todo
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T> dbQuery(block: suspend () -> T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }


class TodoRepositoryImpl : TodoRepository {
    override suspend fun getById(id: Int): Todo? = dbQuery {
        Todos
            .selectAll()
            .where { Todos.id eq id }
            .map(::mapRowToTodo)
            .singleOrNull()
    }

    override suspend fun findAll(): List<Todo> = dbQuery {
        Todos
            .selectAll()
            .orderBy(Todos.id)
            .map(::mapRowToTodo)
    }

    override suspend fun delete(id: Int) {
        dbQuery {
            Todos.deleteWhere { Todos.id eq id }
        }
    }

    override suspend fun create(task: String): Todo {
        val insert = dbQuery {
            Todos.insert {
                it[Todos.task] = task
                it[done] = false
            }
        }
        return getById(insert[Todos.id].value)!!
    }

    override suspend fun update(todo: Todo): Todo {
        dbQuery {
            Todos.update({ Todos.id eq todo.id }) {
                it[task] = todo.task
                it[done] = todo.done
            }
        }
        return getById(todo.id)!!
    }

    private fun mapRowToTodo(row: ResultRow) = Todo(
        id = row[Todos.id].value,
        task = row[Todos.task],
        done = row[Todos.done]
    )

}