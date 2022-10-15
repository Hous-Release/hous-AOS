package hous.release.domain.repository

import hous.release.domain.entity.TodoDetail
import hous.release.domain.entity.response.MemberTodoContent
import hous.release.domain.entity.response.TodoMain

interface TodoRepository {
    suspend fun getTodoMainContent(): Result<TodoMain>
    suspend fun checkTodo(todoId: Int, isChecked: Boolean)
    suspend fun getDailyTodos(): Result<List<TodoMain>>
    suspend fun getMemberTodos(): Result<List<MemberTodoContent>>
    suspend fun getTodoDetail(todoId: Int): Result<TodoDetail>
    suspend fun deleteTodo(todoId: Int)
}
