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

    override suspend fun getHomieProfile(): Result<Profile> =
        kotlin.runCatching {
            profileDataSource.getHomieProfile()
        }.map { response -> response.data.toProfile() }
}
