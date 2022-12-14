package hous.release.data.datasource

import hous.release.data.entity.request.EditHousNameRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.HousResponse
import hous.release.data.entity.response.NoDataResponse
import hous.release.data.service.HousService
import javax.inject.Inject

class HousDataSource @Inject constructor(
    private val housService: HousService
) {
    suspend fun getHome(): BaseResponse<HousResponse> =
        housService.getHome()

    suspend fun putHousName(name: String): NoDataResponse =
        housService.putHousName(EditHousNameRequest(name))
}
