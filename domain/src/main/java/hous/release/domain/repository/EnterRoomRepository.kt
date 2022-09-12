package hous.release.domain.repository

import hous.release.domain.entity.request.CreateRoomRequest
import hous.release.domain.entity.response.BaseResponse
import hous.release.domain.entity.response.CreateRoomResponse

interface EnterRoomRepository {
    suspend fun postCreateRoom(createRoomRequest: CreateRoomRequest): Result<BaseResponse<CreateRoomResponse>>
}
