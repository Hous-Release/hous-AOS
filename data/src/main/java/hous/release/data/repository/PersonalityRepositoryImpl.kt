package hous.release.data.repository

import hous.release.data.datasource.PersonalityDataSource
import hous.release.domain.entity.PersonalityTest
import hous.release.domain.entity.response.PersonalityResult
import hous.release.domain.repository.PersonalityRepository
import javax.inject.Inject

class PersonalityRepositoryImpl @Inject constructor(
    private val personalityDataSource: PersonalityDataSource
) : PersonalityRepository {
    override suspend fun getPersonalityResult(color: String): Result<PersonalityResult> =
        kotlin.runCatching {
            personalityDataSource.getPersonalityResult(color = color)
        }.map { response -> response.data.toPersonalityResult() }

    override suspend fun getPersonalityTests(): Result<List<PersonalityTest>> =
        runCatching {
            personalityDataSource.getPersonalityTests().data
                .map { test -> test.toPersonalityTest() }
        }
}
