package hous.release.data.datasource

import hous.release.data.entity.request.ToDoCheckRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.ToDoMainResponse
import hous.release.data.service.ToDoService
import javax.inject.Inject

class ToDoDataSource @Inject constructor(
    private val toDoService: ToDoService
) {
    suspend fun getToDoMainContent(): BaseResponse<ToDoMainResponse> =
        toDoService.getToDoMainContent()

    suspend fun checkToDo(todoId: Int, isChecked: Boolean) {
        toDoService.checkToDo(todoId, ToDoCheckRequest(isChecked))
    }
}
