package hous.release.domain.repository

import hous.release.domain.entity.response.Profile

interface ProfileRepository {
    suspend fun getProfile(): Result<Profile>

    suspend fun putProfile(
        birthday: String,
        introduction: String?,
        isPublic: Boolean,
        job: String?,
        mbti: String?,
        nickname: String
    ): Result<Boolean>

    suspend fun getHomieProfile(homieId: Int): Result<Profile>
}
