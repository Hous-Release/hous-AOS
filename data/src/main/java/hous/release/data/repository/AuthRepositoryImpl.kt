package hous.release.data.repository

import hous.release.data.datasource.AuthDataSource
import hous.release.data.datasource.LocalPrefSkipTutorialDataSource
import hous.release.domain.entity.response.Login
import hous.release.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val localPrefSkipTutorialDataSource: LocalPrefSkipTutorialDataSource
) : AuthRepository {
    override suspend fun postLogin(
        fcmToken: String,
        socialType: String,
        token: String
    ): Login {
        val response = authDataSource.postLogin(
            fcmToken,
            socialType,
            token
        )
        return when (response.code()) {
            200 -> Login(
                response.code(),
                response.body()!!.data.isJoiningRoom,
                response.body()!!.data.token,
                response.body()!!.data.userId
            )
            400 -> Login().copy(status = response.code())
            401 -> Login().copy(status = response.code())
            404 -> Login().copy(status = response.code())
            else -> Login().copy(status = response.code())
        }
    }

    override suspend fun initSkipTutorial(skipTutorial: Boolean) {
        localPrefSkipTutorialDataSource.showTutorial = skipTutorial
    }

    companion object {
        private const val UNKNOWN_ERROR = "네트워크 통신 중 알 수 없는 오류"
    }
}
