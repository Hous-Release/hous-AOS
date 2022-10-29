package hous.release.domain.repository

import hous.release.domain.entity.response.Profile

interface ProfileRepository {
    suspend fun getProfile(): Result<Profile>
}
