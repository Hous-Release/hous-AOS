package hous.release.data.service

import hous.release.data.model.WrapperClass
import hous.release.data.model.request.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("v1/auth/login")
    suspend fun kakaoLogin(
        @Body body: LoginRequest
    ): WrapperClass<Any>
}
