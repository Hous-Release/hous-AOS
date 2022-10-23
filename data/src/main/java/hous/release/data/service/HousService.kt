package hous.release.data.service

import hous.release.data.entity.request.EditHousNameRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.HousResponse
import hous.release.data.entity.response.NoDataResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface HousService {
    @GET("/v1/home")
    suspend fun getHome(): BaseResponse<HousResponse>

    @PUT("/v1/room/name")
    suspend fun putHousName(
        @Body body: EditHousNameRequest
    ): NoDataResponse
}
