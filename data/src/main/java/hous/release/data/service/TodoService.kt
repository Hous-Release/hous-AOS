package hous.release.data.service

import hous.release.data.entity.request.ToDoCheckRequest
import hous.release.data.entity.request.UpdateToDoUsersRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.EditToDoContentResponse
import hous.release.data.entity.response.NoDataResponse
import hous.release.data.entity.response.ToDoMainResponse
import hous.release.data.entity.response.ToDoUsersResponse
import hous.release.data.entity.response.TodoDetailResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoService {
    @GET("/v1/todos")
    suspend fun getTodoMainContent(): BaseResponse<ToDoMainResponse>

    @POST("/v1/todo/{todoId}/check")
    suspend fun checkTodo(
        @Path("todoId") todoId: Int,
        @Body body: ToDoCheckRequest
    )

    @GET("/v1/todo/{todoId}/summary")
    suspend fun getTodoDetail(
        @Path("todoId") todoId: Int
    ): BaseResponse<TodoDetailResponse>

    @DELETE("/v1/todo/{todoId}")
    suspend fun deleteTodo(@Path("todoId") todoId: Int)

    @GET("/v1/todo")
    suspend fun getTodoUsers(): BaseResponse<ToDoUsersResponse>

    @POST("/v1/todo")
    suspend fun postAddTodo(@Body updateToDoUser: UpdateToDoUsersRequest): NoDataResponse

    @GET("/v1/todo/{todoId}")
    suspend fun getEditTodoContent(@Path("todoId") todoId: Int): BaseResponse<EditToDoContentResponse>

    @PUT("/v1/todo/{todoId}")
    suspend fun putEditToDo(
        @Path("todoId") todoId: Int,
        @Body updateToDoUsersRequest: UpdateToDoUsersRequest
    ): NoDataResponse
}
