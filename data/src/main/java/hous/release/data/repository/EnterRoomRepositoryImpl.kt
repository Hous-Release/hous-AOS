package hous.release.data.repository

import hous.release.data.datasource.EnterRoomDataSource
import hous.release.data.entity.request.CreateRoomRequest
import hous.release.domain.entity.request.DomainCreateRoomRequest
import hous.release.domain.entity.response.DomainCreateRoomResponse
import hous.release.domain.entity.response.DomainEnterRoomResponse
import hous.release.domain.entity.response.DomainGetRoomResponse
import hous.release.domain.repository.EnterRoomRepository
import javax.inject.Inject

class EnterRoomRepositoryImpl @Inject constructor(
    private val enterRoomDataSource: EnterRoomDataSource
) : EnterRoomRepository {
    override suspend fun postCreateRoom(createRoomRequest: DomainCreateRoomRequest): Result<DomainCreateRoomResponse> =
        kotlin.runCatching { enterRoomDataSource.postCreateRoom(CreateRoomRequest(name = createRoomRequest.name)) }
            .map { response ->
                DomainCreateRoomResponse(
                    roomCode = response.data.roomCode,
                    roomId = response.data.roomId
                )
            }

    override suspend fun getEnterRoomCode(roomCode: String): Result<DomainGetRoomResponse> =
        kotlin.runCatching { enterRoomDataSource.getEnterRoomCode(roomCode) }
            .map { response ->
                DomainGetRoomResponse(
                    nickname = response.data.nickname,
                    roomId = response.data.roomId
                )
            }

    override suspend fun postEnterRoomId(roomId: String): Result<DomainEnterRoomResponse> =
        kotlin.runCatching { enterRoomDataSource.postEnterRoomId(roomId) }
            .map { response ->
                DomainEnterRoomResponse(
                    roomCode = response.data.roomCode,
                    roomId = response.data.roomId
                )
            }
}
