package hous.release.data.service

import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.ProfileResponse
import retrofit2.http.GET

interface ProfileService {
    @GET("/v1/user")
    suspend fun getProfile(): BaseResponse<ProfileResponse>
}
