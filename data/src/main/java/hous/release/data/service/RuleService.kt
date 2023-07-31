package hous.release.data.service

import hous.release.data.entity.request.DeleteRulesRequest
import hous.release.data.entity.request.EditRulesRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.NoDataResponse
import hous.release.data.entity.response.rule.CanAddRuleResponse
import hous.release.data.entity.response.rule.DetailRuleResponse
import hous.release.data.entity.response.rule.MainRulesResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface RuleService {
    @GET("/v1/rules")
    suspend fun getMainRules(): BaseResponse<MainRulesResponse>

    @GET("/v2/rule/{id}")
    suspend fun getDetailRuleBy(@Path("id") id: Int): BaseResponse<DetailRuleResponse>

    @GET("/v2/rule/addable")
    suspend fun getAddableRules(): BaseResponse<CanAddRuleResponse>

    @Multipart
    @POST("/v2/rule")
    suspend fun postNewRule(
        @Query("description") description: String,
        @Query("name") name: String,
        @Part images: List<MultipartBody.Part>?
    )

    @PUT("/v1/rules")
    suspend fun putEditedRuleContent(@Body body: EditRulesRequest): NoDataResponse

    @HTTP(method = "DELETE", path = "/v1/rules", hasBody = true)
    suspend fun deleteRuleContent(@Body body: DeleteRulesRequest): NoDataResponse
}
