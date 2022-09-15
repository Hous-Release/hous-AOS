package hous.release.data.service

import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.CreateRoomResponse
import hous.release.data.entity.response.EnterRoomCodeResponse
import hous.release.data.entity.response.GetRoomResponse
import hous.release.domain.entity.request.DomainCreateRoomRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface EnterRoomService {
    @POST("/v1/room")
    suspend fun postCreateRoom(
        @Body body: DomainCreateRoomRequest
    ): BaseResponse<CreateRoomResponse>

    @GET("/v1/room/info")
    suspend fun getEnterRoomCode(
        @Query("code") code: String
    ): BaseResponse<GetRoomResponse>

    @POST("/v1/room/{roomId}/join")
    suspend fun postEnterRoomCode(
        @Path("roomId") roomId:String
    ):BaseResponse<EnterRoomCodeResponse>
}
