package hous.release.data.service

import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.LoginResponse
import hous.release.domain.entity.request.DomainLoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/v1/auth/login")
    suspend fun postLogin(
        @Body body: DomainLoginRequest
    ): BaseResponse<LoginResponse>
}
