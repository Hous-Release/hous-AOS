package hous.release.data.service

import hous.release.data.entity.request.CreateRoomRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.CreateRoomResponse
import hous.release.data.entity.response.EnterRoomResponse
import hous.release.data.entity.response.GetRoomDetailResponse
import hous.release.data.entity.response.GetRoomResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface EnterRoomService {
    @POST("/v1/room")
    suspend fun postCreateRoom(
        @Body body: CreateRoomRequest
    ): BaseResponse<CreateRoomResponse>

    @GET("/v1/room")
    suspend fun getIsEnterRoom(): BaseResponse<GetRoomDetailResponse>

    @GET("/v1/room/info")
    suspend fun getEnterRoomCode(
        @Query("code") code: String
    ): BaseResponse<GetRoomResponse>

    @POST("/v1/room/{roomId}/join")
    suspend fun postEnterRoomId(
        @Path("roomId") roomId: String
    ): BaseResponse<EnterRoomResponse>
}
