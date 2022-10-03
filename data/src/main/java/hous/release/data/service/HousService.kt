package hous.release.data.service

import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.HousResponse
import retrofit2.http.GET

interface HousService {
    @GET("/v1/home")
    suspend fun getHome(): BaseResponse<HousResponse>
}
