package hous.release.data.service

import hous.release.data.entity.request.AddRulesRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.OurRulesResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OurRulesService {
    @GET("/v1/rules")
    suspend fun getOurRuleContent(): BaseResponse<List<OurRulesResponse>>

    @POST("/v1/rules")
    suspend fun postAddedRuleContent(@Body body: AddRulesRequest): BaseResponse<Unit>
}
