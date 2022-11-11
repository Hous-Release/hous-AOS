package hous.release.data.service

import hous.release.data.entity.request.ProfileEditRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.NoDataResponse
import hous.release.data.entity.response.ProfileResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProfileService {
    @GET("/v1/user")
    suspend fun getProfile(): BaseResponse<ProfileResponse>

    @PUT("/v1/user")
    suspend fun putProfile(@Body body: ProfileEditRequest): NoDataResponse

    @GET("/v1/user/{homieId}")
    suspend fun getHomieProfile(@Path("homieId") homieId: Int): BaseResponse<ProfileResponse>
}
