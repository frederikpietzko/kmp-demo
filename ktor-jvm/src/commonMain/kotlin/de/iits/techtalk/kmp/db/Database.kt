package de.iits.techtalk.kmp.db

import de.iits.techtalk.kmp.model.Todo

interface TodoRepository {
    suspend fun getById(id: Int): Todo?
    suspend fun findAll(): List<Todo>
    suspend fun delete(id: Int)
    suspend fun create(task: String): Todo
    suspend fun update(todo: Todo): Todo
}

expect fun getTodoRepository(): TodoRepository