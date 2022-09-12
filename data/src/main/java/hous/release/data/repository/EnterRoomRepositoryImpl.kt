package hous.release.data.repository

import hous.release.data.datasource.EnterRoomDataSource
import hous.release.domain.entity.request.CreateRoomRequest
import hous.release.domain.entity.response.BaseResponse
import hous.release.domain.entity.response.CreateRoomResponse
import hous.release.domain.repository.EnterRoomRepository
import javax.inject.Inject

class EnterRoomRepositoryImpl @Inject constructor(
    private val enterRoomDataSource: EnterRoomDataSource
) : EnterRoomRepository {
    override suspend fun postCreateRoom(createRoomRequest: CreateRoomRequest): Result<BaseResponse<CreateRoomResponse>> =
        kotlin.runCatching {
            enterRoomDataSource.postCreateRoom(createRoomRequest)
        }
}
