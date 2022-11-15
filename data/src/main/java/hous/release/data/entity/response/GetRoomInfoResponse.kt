package hous.release.data.entity.response

import hous.release.domain.entity.response.RoomInfo

data class GetRoomInfoResponse(
    val isJoiningRoom: Boolean,
    val roomCode: String,
    val roomId: Int
) {
    fun toRoomInfo() = RoomInfo(
        isJoiningRoom = isJoiningRoom,
        roomCode = roomCode,
        roomId = roomId
    )
}
