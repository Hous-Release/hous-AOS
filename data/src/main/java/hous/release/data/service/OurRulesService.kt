package hous.release.data.service

import hous.release.data.entity.request.AddRulesRequest
import hous.release.data.entity.request.DeleteRulesRequest
import hous.release.data.entity.request.EditRulesRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.NoDataResponse
import hous.release.data.entity.response.rule.OurRulesResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT

interface OurRulesService {
    @GET("/v1/rules")
    suspend fun getOurRuleContent(): BaseResponse<OurRulesResponse>

    @POST("/v1/rules")
    suspend fun postAddedRuleContent(@Body body: AddRulesRequest): NoDataResponse

    @PUT("/v1/rules")
    suspend fun putEditedRuleContent(@Body body: EditRulesRequest): NoDataResponse

    @HTTP(method = "DELETE", path = "/v1/rules", hasBody = true)
    suspend fun deleteRuleContent(@Body body: DeleteRulesRequest): NoDataResponse
}
