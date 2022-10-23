package hous.release.domain.repository

import hous.release.domain.entity.response.Login
import hous.release.domain.entity.response.SignUp

interface AuthRepository {
    suspend fun postLogin(
        fcmToken: String,
        socialType: String,
        token: String
    ): Result<Login>

    suspend fun postSignUp(
        birthday: String,
        isPublic: Boolean,
        nickname: String,
    ): Result<SignUp>

    suspend fun initToken(fcmToken: String, socialType: String, token: String)

    suspend fun initSkipTutorial(skipTutorial: Boolean)
}
