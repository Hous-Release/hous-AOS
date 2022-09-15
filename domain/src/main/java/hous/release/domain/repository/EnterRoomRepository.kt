package hous.release.domain.repository

import hous.release.domain.entity.request.DomainCreateRoomRequest
import hous.release.domain.entity.response.DomainCreateRoomResponse
import hous.release.domain.entity.response.DomainGetRoomResponse

interface EnterRoomRepository {
    suspend fun postCreateRoom(createRoomRequest: DomainCreateRoomRequest): Result<DomainCreateRoomResponse>

    suspend fun getEnterRoomCode(roomCode: String): Result<DomainGetRoomResponse>
}
