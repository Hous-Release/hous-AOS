package hous.release.data.repository

import hous.release.data.datasource.EnterRoomDataSource
import hous.release.data.entity.request.CreateRoomRequest
import hous.release.domain.entity.request.DomainCreateRoomRequest
import hous.release.domain.entity.response.DomainCreateRoomResponse
import hous.release.domain.entity.response.DomainGetRoomResponse
import hous.release.domain.repository.EnterRoomRepository
import javax.inject.Inject

class EnterRoomRepositoryImpl @Inject constructor(
    private val enterRoomDataSource: EnterRoomDataSource
) : EnterRoomRepository {
    override suspend fun postCreateRoom(createRoomRequest: DomainCreateRoomRequest): Result<DomainCreateRoomResponse> {
        kotlin.runCatching { enterRoomDataSource.postCreateRoom(CreateRoomRequest(name = createRoomRequest.name)) }
            .onSuccess { response ->
                return Result.success(
                    DomainCreateRoomResponse(
                        roomCode = response.data.roomCode,
                        roomId = response.data.roomId
                    )
                )
            }
            .onFailure { throw it }

        throw IllegalStateException(UNKNOWN_ERROR)
    }

    override suspend fun getEnterRoomCode(roomCode: String): Result<DomainGetRoomResponse> {
        kotlin.runCatching { enterRoomDataSource.getEnterRoomCode(roomCode) }
            .onSuccess { response ->
                return Result.success(
                    DomainGetRoomResponse(
                        nickname = response.data.nickname,
                        roomId = response.data.roomId
                    )
                )
            }
            .onFailure { throw it }

        throw IllegalStateException(UNKNOWN_ERROR)
    }

    companion object {
        const val UNKNOWN_ERROR = "네트워크 통신 중 알 수 없는 오류"
    }
}
