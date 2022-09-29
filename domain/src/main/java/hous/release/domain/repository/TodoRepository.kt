package hous.release.domain.repository

import hous.release.domain.entity.response.TodoMain

interface TodoRepository {
    suspend fun getTodoMainContent(): Result<TodoMain>
    suspend fun checkTodo(todoId: Int, isChecked: Boolean)
    suspend fun getDailyTodos(): Result<List<TodoMain>>
}
