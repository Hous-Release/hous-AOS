package hous.release.data.entity.response

import hous.release.domain.entity.response.RoomDetail

data class GetRoomDetailResponse(
    val isJoiningRoom: Boolean,
    val roomCode: String,
    val roomId: Int
) {
    fun toRoomDetail() = RoomDetail(
        isJoiningRoom = isJoiningRoom,
        roomCode = roomCode,
        roomId = roomId
    )
}
