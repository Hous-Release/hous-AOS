package hous.release.data.datasource

import hous.release.data.entity.request.CreateRoomRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.CreateRoomResponse
import hous.release.data.entity.response.EnterRoomResponse
import hous.release.data.entity.response.GetRoomResponse
import hous.release.data.service.EnterRoomService
import hous.release.domain.entity.request.DomainCreateRoomRequest
import javax.inject.Inject

class EnterRoomDataSource @Inject constructor(
    private val enterRoomService: EnterRoomService
) {
    suspend fun postCreateRoom(createRoomRequest: CreateRoomRequest): BaseResponse<CreateRoomResponse> =
        enterRoomService.postCreateRoom(DomainCreateRoomRequest(name = createRoomRequest.name))

    suspend fun getEnterRoomCode(roomCode: String): BaseResponse<GetRoomResponse> =
        enterRoomService.getEnterRoomCode(roomCode)

    suspend fun postEnterRoomId(roomId: String): BaseResponse<EnterRoomResponse> =
        enterRoomService.postEnterRoomId(roomId)
}
