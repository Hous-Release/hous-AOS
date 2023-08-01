package hous.release.data.datasource

import android.content.SharedPreferences
import hous.release.data.entity.request.LoginRequest
import hous.release.data.entity.request.SignUpRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.LoginResponse
import hous.release.data.entity.response.NoDataResponse
import hous.release.data.entity.response.SignUpResponse
import hous.release.data.service.AuthService
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authService: AuthService,
    private val prefs: SharedPreferences
) {
    suspend fun postLogin(
        fcmToken: String,
        socialType: String,
        token: String
    ): BaseResponse<LoginResponse> =
        authService.postLogin(LoginRequest(fcmToken, socialType, token))

    suspend fun postSignUp(
        birthday: String,
        fcmToken: String,
        isPublic: Boolean,
        nickname: String,
        socialType: String,
        token: String
    ): BaseResponse<SignUpResponse> =
        authService.postSignUp(
            SignUpRequest(
                birthday,
                fcmToken,
                isPublic,
                nickname,
                socialType,
                token
            )
        )

    /** TODO 영주 : 회원탈퇴 api 구현 */
    suspend fun deleteUser(): NoDataResponse =
        authService.deleteUser()

    suspend fun postLogout(): NoDataResponse =
        authService.postLogout()

    fun clearLocalPref() {
        with(prefs.edit()) {
            clear()
            commit()
        }
    }

    suspend fun postForceLogin(
        fcmToken: String,
        socialType: String,
        token: String
    ): BaseResponse<LoginResponse> =
        authService.postForceLogin(LoginRequest(fcmToken, socialType, token))
}
