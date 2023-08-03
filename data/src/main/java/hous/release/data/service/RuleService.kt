package hous.release.data.service

import hous.release.data.entity.request.DeleteRulesRequest
import hous.release.data.entity.request.EditRulesRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.NoDataResponse
import hous.release.data.entity.response.rule.DetailRuleResponse
import hous.release.data.entity.response.rule.MainRulesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface RuleService {
    @GET("/v1/rules")
    suspend fun getMainRules(): BaseResponse<MainRulesResponse>

    @GET("/v2/rule/{id}")
    suspend fun getDetailRuleBy(@Path("id") id: Int): BaseResponse<DetailRuleResponse>

    @Multipart
    @POST("/v2/rules")
    suspend fun postNewRule(
        @Part("description") description: RequestBody,
        @Part("name") name: RequestBody,
        @Part images: List<MultipartBody.Part>
    ): NoDataResponse

    @PUT("/v1/rules")
    suspend fun putEditedRuleContent(@Body body: EditRulesRequest): NoDataResponse

    @HTTP(method = "DELETE", path = "/v1/rules", hasBody = true)
    suspend fun deleteRuleContent(@Body body: DeleteRulesRequest): NoDataResponse
}
