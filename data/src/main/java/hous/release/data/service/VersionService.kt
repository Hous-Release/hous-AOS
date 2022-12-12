package hous.release.data.service

import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.VersionCheckResponse
import retrofit2.http.GET

interface VersionService {
    @GET("/v1/version")
    suspend fun getVersionCheck(): BaseResponse<VersionCheckResponse>
}
