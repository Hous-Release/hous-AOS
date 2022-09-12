package hous.release.domain.repository

import hous.release.domain.entity.request.CreateRoomRequest
import hous.release.domain.entity.response.BaseResponse
import hous.release.domain.entity.response.CreateRoomResponse
import hous.release.domain.entity.response.EnterRoomCodeResponse

interface EnterRoomRepository {
    suspend fun postCreateRoom(createRoomRequest: CreateRoomRequest): Result<BaseResponse<CreateRoomResponse>>

    suspend fun getEnterRoomCode(roomCode: String): Result<BaseResponse<EnterRoomCodeResponse>>
}
