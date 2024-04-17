package de.iits.techtalk.kmp.dto

import kotlinx.serialization.Serializable

@Serializable
data class TodoDto(
    val id: Long,
    val task: String,
    val done: Boolean
)

@Serializable
data class CreateTodoRequest(
    val task: String,
)
