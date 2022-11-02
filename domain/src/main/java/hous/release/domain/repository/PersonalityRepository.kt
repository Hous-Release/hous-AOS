package hous.release.domain.repository

import hous.release.domain.entity.response.PersonalityResult

interface PersonalityRepository {
    suspend fun getPersonalityResult(color: String): Result<PersonalityResult>
}
