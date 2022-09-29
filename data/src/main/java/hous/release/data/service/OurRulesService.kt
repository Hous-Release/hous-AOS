package hous.release.data.service

import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.OurRulesResponse
import retrofit2.http.GET

interface OurRulesService {
    @GET("/v1/rules")
    suspend fun getOurRuleContent(): BaseResponse<List<OurRulesResponse>>
}
