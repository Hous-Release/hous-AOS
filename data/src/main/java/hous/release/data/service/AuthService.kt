package hous.release.data.service

import hous.release.data.entity.request.LoginRequest
import hous.release.data.entity.request.SignUpRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.LoginResponse
import hous.release.data.entity.response.NoDataResponse
import hous.release.data.entity.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.POST

interface AuthService {
    @POST("/v1/auth/login")
    suspend fun postLogin(
        @Body body: LoginRequest
    ): BaseResponse<LoginResponse>

    @POST("/v1/auth/signup")
    suspend fun postSignUp(
        @Body body: SignUpRequest
    ): BaseResponse<SignUpResponse>

    /** TODO 영주 : 회원탈퇴 api 구현 */
    @HTTP(method = "DELETE", path = "/v1/user", hasBody = true)
    suspend fun deleteUser(): NoDataResponse

    @POST("/v1/auth/logout")
    suspend fun postLogout(): NoDataResponse

    @POST("/v1/auth/login/force")
    suspend fun postForceLogin(
        @Body body: LoginRequest
    ): BaseResponse<LoginResponse>
}
