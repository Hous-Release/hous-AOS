package hous.release.domain.repository

import hous.release.domain.entity.ApiResult
import hous.release.domain.entity.TodoDetail
import hous.release.domain.entity.response.ToDoContent
import hous.release.domain.entity.response.ToDoUser
import hous.release.domain.entity.response.TodoMain
import hous.release.domain.entity.todo.FilteredTodo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun getFilteredTodos(
        dayOfWeeks: List<String>?,
        onboardingIds: List<Int>?
    ): Result<FilteredTodo>

    suspend fun getIsAddableTodo(): Result<Boolean>

    suspend fun getTodoMainContent(): Result<TodoMain>
    suspend fun checkTodo(todoId: Int, isChecked: Boolean): Result<Unit>
    suspend fun getTodoDetail(todoId: Int): Result<TodoDetail>
    suspend fun deleteTodo(todoId: Int): Result<Unit>
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
