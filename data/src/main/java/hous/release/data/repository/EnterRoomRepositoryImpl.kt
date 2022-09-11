package hous.release.data.repository

import hous.release.data.datasource.EnterRoomDataSource
import hous.release.domain.entity.request.CreateRoomRequest
import hous.release.domain.entity.response.NoDataResponse
import hous.release.domain.repository.EnterRoomRepository
import javax.inject.Inject

class EnterRoomRepositoryImpl @Inject constructor(
    private val enterRoomDataSource: EnterRoomDataSource
) : EnterRoomRepository {
    override suspend fun postCreateRoom(createRoomRequest: CreateRoomRequest): Result<NoDataResponse> =
        kotlin.runCatching {
            enterRoomDataSource.postCreateRoom(createRoomRequest)
        }
}
