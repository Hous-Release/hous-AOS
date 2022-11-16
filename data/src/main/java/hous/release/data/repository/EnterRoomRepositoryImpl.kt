package hous.release.data.repository

import hous.release.data.datasource.EnterRoomDataSource
import hous.release.domain.entity.response.NewRoom
import hous.release.domain.entity.response.Room
import hous.release.domain.entity.response.RoomDetail
import hous.release.domain.repository.EnterRoomRepository
import javax.inject.Inject

class EnterRoomRepositoryImpl @Inject constructor(
    private val enterRoomDataSource: EnterRoomDataSource
) : EnterRoomRepository {
    override suspend fun postCreateRoom(roomName: String): Result<NewRoom> =
        kotlin.runCatching { enterRoomDataSource.postCreateRoom(roomName) }
            .map { response ->
                NewRoom(
                    roomCode = response.data.roomCode,
                    roomId = response.data.roomId
                )
            }

    override suspend fun getEnterRoomCode(roomCode: String): Result<Room> =
        kotlin.runCatching { enterRoomDataSource.getEnterRoomCode(roomCode) }
            .map { response ->
                Room(
                    nickname = response.data.nickname,
                    roomId = response.data.roomId
                )
            }

    override suspend fun postEnterRoomId(roomId: String): Result<NewRoom> =
        kotlin.runCatching { enterRoomDataSource.postEnterRoomId(roomId) }
            .map { response ->
                NewRoom(
                    roomCode = response.data.roomCode,
                    roomId = response.data.roomId
                )
            }

    override suspend fun getEnterRoomInfo(): Result<RoomDetail> =
        runCatching { enterRoomDataSource.getEnterRoomInfo().data.toRoomDetail() }
}
