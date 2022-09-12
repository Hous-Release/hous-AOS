package hous.release.data.service

import hous.release.domain.entity.request.CreateRoomRequest
import hous.release.domain.entity.response.BaseResponse
import hous.release.domain.entity.response.CreateRoomResponse
import hous.release.domain.entity.response.EnterRoomCodeResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface EnterRoomService {
    @POST("/v1/room")
    suspend fun postCreateRoom(
        @Body body: CreateRoomRequest
    ): BaseResponse<CreateRoomResponse>

    @GET("/v1/room/info")
    suspend fun getEnterRoomCode(
        @Query("code") code: String
    ): BaseResponse<EnterRoomCodeResponse>
}
