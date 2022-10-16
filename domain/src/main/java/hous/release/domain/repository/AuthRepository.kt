package hous.release.domain.repository

import hous.release.domain.entity.response.Login

interface AuthRepository {
    suspend fun postLogin(fcmToken: String, socialType: String, token: String): Result<Login>

    suspend fun initShowTutorial(skipTutorial: Boolean)

    suspend fun getShowTutorial(): Boolean
}
