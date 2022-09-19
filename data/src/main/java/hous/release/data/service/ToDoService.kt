package hous.release.data.service

import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.ToDoMainResponse
import retrofit2.http.GET

interface ToDoService {
    @GET("v1/todos")
    suspend fun getToDoMainContent(): BaseResponse<ToDoMainResponse>
}
