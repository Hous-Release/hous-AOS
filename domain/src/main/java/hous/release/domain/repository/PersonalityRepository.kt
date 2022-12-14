package hous.release.domain.repository

import hous.release.domain.entity.PersonalityTest
import hous.release.domain.entity.response.PersonalityResult

interface PersonalityRepository {
    suspend fun getPersonalityResult(color: String): Result<PersonalityResult>
    suspend fun getPersonalityTests(): Result<List<PersonalityTest>>
    suspend fun putPersonalityTestResult(
        cleanScore: Int,
        introversionScore: Int,
        lightScore: Int,
        noiseScore: Int,
        smellScore: Int
    ): Result<String>
}
