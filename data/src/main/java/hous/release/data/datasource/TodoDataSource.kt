package hous.release.data.datasource

import hous.release.data.entity.request.ToDoCheckRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.ToDoMainResponse
import hous.release.data.service.TodoService
import javax.inject.Inject

class TodoDataSource @Inject constructor(
    private val toDoService: TodoService
) {
    suspend fun getTodoMainContent(): BaseResponse<ToDoMainResponse> =
        toDoService.getTodoMainContent()

    suspend fun checkTodo(todoId: Int, isChecked: Boolean) {
        toDoService.checkTodo(todoId, ToDoCheckRequest(isChecked))
    }

    suspend fun getDailyTodos() = toDoService.getDailyTodos()

    suspend fun getTodoDetail(todoId: Int) = toDoService.getTodoDetail(todoId)

    suspend fun deleteTodo(todoId: Int) = toDoService.deleteTodo(todoId)
}
