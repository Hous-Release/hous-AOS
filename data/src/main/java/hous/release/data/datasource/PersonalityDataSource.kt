package hous.release.data.datasource

import hous.release.data.entity.PersonalityTestEntity
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.PersonalityResultResponse
import hous.release.data.service.PersonalityService
import javax.inject.Inject

class PersonalityDataSource @Inject constructor(
    private val personalityService: PersonalityService
) {
    suspend fun getPersonalityResult(color: String): BaseResponse<PersonalityResultResponse> =
        personalityService.getPersonalityResult(color = color)

    suspend fun getPersonalityTests(): BaseResponse<List<PersonalityTestEntity>> =
        personalityService.getPersonalityTests()
}
