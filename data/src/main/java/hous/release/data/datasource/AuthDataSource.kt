package hous.release.data.datasource

import android.content.SharedPreferences
import hous.release.data.entity.request.DeleteUserRequest
import hous.release.data.entity.request.LoginRequest
import hous.release.data.entity.request.SignUpRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.LoginResponse
import hous.release.data.entity.response.NoDataResponse
import hous.release.data.entity.response.SignUpResponse
import hous.release.data.service.AuthService
import hous.release.domain.entity.FeedbackType
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

    suspend fun deleteUser(feedbackType: FeedbackType, comment: String): NoDataResponse =
        authService.deleteUser(
            DeleteUserRequest(
                feedbackType = feedbackType.name,
                comment = comment
            )
        )

    fun clearLocalPref() {
        with(prefs.edit()) {
            clear()
            commit()
        }
    }
}
