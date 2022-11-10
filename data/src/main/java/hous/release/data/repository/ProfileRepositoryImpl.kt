package hous.release.data.repository

import hous.release.data.datasource.ProfileDataSource
import hous.release.domain.entity.response.Profile
import hous.release.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileDataSource: ProfileDataSource
) : ProfileRepository {
    override suspend fun getProfile(): Result<Profile> =
        kotlin.runCatching {
            profileDataSource.getProfile()
        }.map { response -> response.data.toProfile() }

    override suspend fun putProfile(
        birthday: String,
        introduction: String?,
        isPublic: Boolean,
        job: String?,
        mbti: String?,
        nickname: String
    ): Result<Boolean> =
        kotlin.runCatching {
            profileDataSource.putProfile(
                birthday,
                introduction,
                isPublic,
                job,
                mbti,
                nickname
            )
        }.map { response -> response.success }
}
