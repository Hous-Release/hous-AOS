package hous.release.data.datasource

import hous.release.data.service.EnterRoomService
import hous.release.domain.entity.request.CreateRoomRequest
import hous.release.domain.entity.response.NoDataResponse
import javax.inject.Inject

class EnterRoomDataSource @Inject constructor(
    private val enterRoomService: EnterRoomService
) {
    suspend fun postCreateRoom(createRoomRequest: CreateRoomRequest): NoDataResponse =
        enterRoomService.postCreateRoom(createRoomRequest)
}
