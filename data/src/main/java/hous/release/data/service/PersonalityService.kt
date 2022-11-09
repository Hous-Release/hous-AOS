package hous.release.data.service

import hous.release.data.entity.PersonalityTestEntity
import hous.release.data.entity.TestScoreEntity
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.ColorEntity
import hous.release.data.entity.response.PersonalityResultResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface PersonalityService {
    @GET("/v1/user/personality")
    suspend fun getPersonalityResult(
        @Query("color") color: String
    ): BaseResponse<PersonalityResultResponse>

    @GET("/v1/user/personality/test")
    suspend fun getPersonalityTests(): BaseResponse<List<PersonalityTestEntity>>

    @PUT("/v1/user/personality")
    suspend fun putPersonalityTestResult(
        @Body body: TestScoreEntity
    ): BaseResponse<ColorEntity>
}
