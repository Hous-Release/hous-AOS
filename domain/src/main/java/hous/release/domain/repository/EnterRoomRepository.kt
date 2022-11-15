package hous.release.domain.repository

import hous.release.domain.entity.response.NewRoom
import hous.release.domain.entity.response.Room
import hous.release.domain.entity.response.RoomInfo

interface EnterRoomRepository {
    suspend fun postCreateRoom(roomName: String): Result<NewRoom>

    suspend fun getEnterRoomCode(roomCode: String): Result<Room>

    suspend fun postEnterRoomId(roomId: String): Result<NewRoom>

    suspend fun getEnterRoomInfo(): Result<RoomInfo>
}
