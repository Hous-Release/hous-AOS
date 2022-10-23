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
    ): Result<Login> =
        kotlin.runCatching {
            authDataSource.postLogin(
                fcmToken,
                socialType,
                token
            )
        }.map { response -> response.data.toLogin() }

    override suspend fun initSkipTutorial(skipTutorial: Boolean) {
        localPrefSkipTutorialDataSource.showTutorial = skipTutorial
    }
}
