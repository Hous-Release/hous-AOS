package hous.release.data.datasource

import hous.release.data.entity.request.ToDoCheckRequest
import hous.release.data.entity.request.UpdateToDoUsersRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.ToDoMainResponse
import hous.release.data.entity.response.todo.FilteredTodoResponse
import hous.release.data.entity.response.todo.IsAddableTodoResponse
import hous.release.data.service.TodoService
import hous.release.domain.entity.UpdateToDoUser
import javax.inject.Inject

class TodoDataSource @Inject constructor(
    private val toDoService: TodoService
) {
    suspend fun getTodoMainContent(): BaseResponse<ToDoMainResponse> =
        toDoService.getTodoMainContent()

    suspend fun getFilteredTodos(
        dayOfWeeks: List<String>?,
        onboardingIds: List<Int>?
    ): BaseResponse<FilteredTodoResponse> =
        toDoService.getFilteredTodos(
            dayOfWeeks = dayOfWeeks?.map { transformDayOfWeek(it) },
            onboardingIds = onboardingIds
        )

    suspend fun getIsAddableTodo(): BaseResponse<IsAddableTodoResponse> =
        toDoService.getIsAddableTodo()

    suspend fun checkTodo(todoId: Int, isChecked: Boolean) {
        toDoService.checkTodo(todoId, ToDoCheckRequest(isChecked))
    }

    suspend fun getTodoDetail(todoId: Int) = toDoService.getTodoDetail(todoId)

    suspend fun deleteTodo(todoId: Int) = toDoService.deleteTodo(todoId)

    suspend fun getToDoUsers() = toDoService.getTodoUsers()

    suspend fun postAddTodo(
        isPushNotification: Boolean,
        updateTodoUsers: List<UpdateToDoUser>,
        toDoName: String
    ) = toDoService.postAddTodo(
        UpdateToDoUsersRequest(
            isPushNotification = isPushNotification,
            todoUsers = updateTodoUsers,
            name = toDoName
        )
    )

    suspend fun getEditTodoContent(todoId: Int) = toDoService.getEditTodoContent(todoId = todoId)
    suspend fun putEditToDo(
        todoId: Int,
        isPushNotification: Boolean,
        updateTodoUsers: List<UpdateToDoUser>,
        toDoName: String
    ) = toDoService.putEditToDo(
        todoId = todoId,
        UpdateToDoUsersRequest(
            isPushNotification = isPushNotification,
            todoUsers = updateTodoUsers,
            name = toDoName
        )
    )

    private fun transformDayOfWeek(dayOfWeek: String): String = when (dayOfWeek) {
        "월" -> "MONDAY"
        "화" -> "TUESDAY"
        "수" -> "WEDNESDAY"
        "목" -> "THURSDAY"
        "금" -> "FRIDAY"
        "토" -> "SATURDAY"
        else -> "SUNDAY"
    }
}
