package hous.release.data.service

import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.PersonalityResultResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PersonalityService {
    @GET("/v1/user/personality")
    suspend fun getPersonalityResult(
        @Query("color") color: String
    ): BaseResponse<PersonalityResultResponse>
}
