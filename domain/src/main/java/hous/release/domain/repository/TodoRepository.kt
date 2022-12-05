package hous.release.domain.repository

import hous.release.domain.entity.ApiResult
import hous.release.domain.entity.TodoDetail
import hous.release.domain.entity.response.AllMemberTodo
import hous.release.domain.entity.response.DailyTodo
import hous.release.domain.entity.response.ToDoContent
import hous.release.domain.entity.response.ToDoUser
import hous.release.domain.entity.response.TodoMain
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun getTodoMainContent(): Result<TodoMain>
    suspend fun checkTodo(todoId: Int, isChecked: Boolean): Result<Unit>
    suspend fun getDailyTodos(): Result<DailyTodo>
    suspend fun getMemberTodos(): Result<AllMemberTodo>
    suspend fun getTodoDetail(todoId: Int): Result<TodoDetail>
    suspend fun deleteTodo(todoId: Int)
    fun getToDoUsers(): Flow<ApiResult<List<ToDoUser>>>
    fun postAddToDo(
        isPushNotification: Boolean,
        todoUsers: List<ToDoUser>,
        toDoName: String
    ): Flow<ApiResult<String>>

    fun getEditTodoContent(todoId: Int): Flow<ApiResult<ToDoContent>>
    fun putEditToDo(
        todoId: Int,
        isPushNotification: Boolean,
        todoUsers: List<ToDoUser>,
        toDoName: String
    ): Flow<ApiResult<String>>
}
