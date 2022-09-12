package hous.release.data.datasource

import hous.release.data.service.EnterRoomService
import hous.release.domain.entity.request.CreateRoomRequest
import hous.release.domain.entity.response.BaseResponse
import hous.release.domain.entity.response.CreateRoomResponse
import hous.release.domain.entity.response.EnterRoomCodeResponse
import javax.inject.Inject

class EnterRoomDataSource @Inject constructor(
    private val enterRoomService: EnterRoomService
) {
    suspend fun postCreateRoom(createRoomRequest: CreateRoomRequest): BaseResponse<CreateRoomResponse> =
        enterRoomService.postCreateRoom(createRoomRequest)

    suspend fun getEnterRoomCode(roomCode: String): BaseResponse<EnterRoomCodeResponse> =
        enterRoomService.getEnterRoomCode(roomCode)
}
