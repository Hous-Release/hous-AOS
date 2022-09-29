package hous.release.data.service

import hous.release.data.entity.request.ToDoCheckRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.ToDoMainResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TodoService {
    @GET("v1/todos")
    suspend fun getTodoMainContent(): BaseResponse<ToDoMainResponse>

    @POST("v1/todo/{todoId}/check")
    suspend fun checkTodo(
        @Path("todoId") todoId: Int,
        @Body body: ToDoCheckRequest
    )

    @GET("v1/todos/day")
    suspend fun getDailyTodos(): BaseResponse<List<ToDoMainResponse>>
}
