package hous.release.data.datasource

import hous.release.data.entity.request.CreateRoomRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.CreateRoomResponse
import hous.release.data.entity.response.EnterRoomResponse
import hous.release.data.entity.response.GetRoomResponse
import hous.release.data.service.EnterRoomService
import javax.inject.Inject

class EnterRoomDataSource @Inject constructor(
    private val enterRoomService: EnterRoomService
) {
    suspend fun postCreateRoom(roomName: String): BaseResponse<CreateRoomResponse> =
        enterRoomService.postCreateRoom(CreateRoomRequest(name = roomName))

    suspend fun getEnterRoomCode(roomCode: String): BaseResponse<GetRoomResponse> =
        enterRoomService.getEnterRoomCode(roomCode)

    suspend fun postEnterRoomId(roomId: String): BaseResponse<EnterRoomResponse> =
        enterRoomService.postEnterRoomId(roomId)
}
