package hous.release.data.repository

import hous.release.data.datasource.AuthDataSource
import hous.release.data.datasource.LocalPrefSaveTokenDataSource
import hous.release.data.datasource.LocalPrefSkipTutorialDataSource
import hous.release.domain.entity.response.Login
import hous.release.domain.entity.response.SignUp
import hous.release.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val localPrefSkipTutorialDataSource: LocalPrefSkipTutorialDataSource,
    private val localPrefSaveTokenDataSource: LocalPrefSaveTokenDataSource
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

    override suspend fun postSignUp(
        birthday: String,
        fcmToken: String,
        isPublic: Boolean,
        nickname: String,
        socialType: String,
        token: String
    ): Result<SignUp> =
        kotlin.runCatching {
            authDataSource.postSignUp(
                birthday,
                fcmToken,
                isPublic,
                nickname,
                socialType,
                token
            )
        }.map { response -> response.data.toSignUp() }

    override suspend fun initSaveToken(
        saveFcmToken: String?,
        saveSocialType: String?,
        saveToken: String?
    ) {
        localPrefSaveTokenDataSource.fcmToken = saveFcmToken
        localPrefSaveTokenDataSource.socialType = saveSocialType
        localPrefSaveTokenDataSource.token = saveToken
    }

    override suspend fun initSkipTutorial(skipTutorial: Boolean) {
        localPrefSkipTutorialDataSource.showTutorial = skipTutorial
    }
}
