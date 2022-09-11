package hous.release.domain.repository

import hous.release.domain.entity.request.CreateRoomRequest
import hous.release.domain.entity.response.NoDataResponse

interface EnterRoomRepository {
    suspend fun postCreateRoom(createRoomRequest: CreateRoomRequest): Result<NoDataResponse>
}
