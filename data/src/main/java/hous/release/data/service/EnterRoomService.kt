package hous.release.data.service

import hous.release.domain.entity.request.CreateRoomRequest
import hous.release.domain.entity.response.NoDataResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface EnterRoomService {
    @POST("/v1/room")
    suspend fun postCreateRoom(@Body body: CreateRoomRequest): NoDataResponse
}
