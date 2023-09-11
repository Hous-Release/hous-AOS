package hous.release.data.service

import hous.release.data.entity.request.rule.UpdateRepresentRulesRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.NoDataResponse
import hous.release.data.entity.response.rule.CanAddRuleResponse
import hous.release.data.entity.response.rule.DetailRuleResponse
import hous.release.data.entity.response.rule.RulesResponse
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
    suspend fun getRules(): BaseResponse<RulesResponse>

    @GET("/v2/rule/{id}")
    suspend fun getDetailRuleBy(@Path("id") id: Int): BaseResponse<DetailRuleResponse>

    @GET("/v2/rule/addable")
    suspend fun getAddableRules(): BaseResponse<CanAddRuleResponse>

    @Multipart
    @POST("/v2/rule")
    suspend fun addRule(
        @Query("description") description: String,
        @Query("name") name: String,
        @Part images: List<MultipartBody.Part>
    )

    @POST("/v2/rule")
    suspend fun addRuleNoImage(
        @Query("description") description: String,
        @Query("name") name: String
    )

    @Multipart
    @PUT("/v2/rule/{id}")
    suspend fun updateRule(
        @Path("id") id: Int,
        @Query("description") description: String,
        @Query("name") name: String,
        @Part images: List<MultipartBody.Part>
    ): NoDataResponse

    @PUT("/v2/rule/{id}")
    suspend fun updateRuleNoImage(
        @Path("id") id: Int,
        @Query("description") description: String,
        @Query("name") name: String
    ): NoDataResponse

    @PUT("/v1/rules/represent")
    suspend fun updateRepresentRules(
        @Body req: UpdateRepresentRulesRequest
    ): NoDataResponse

    @HTTP(method = "DELETE", path = "/v2/rule/{id}")
    suspend fun deleteRule(@Path("id") ruleId: Int): NoDataResponse
}
