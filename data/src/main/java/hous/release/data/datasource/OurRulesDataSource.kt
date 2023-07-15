package hous.release.data.datasource

import hous.release.data.entity.request.AddRulesRequest
import hous.release.data.entity.request.DeleteRulesRequest
import hous.release.data.entity.request.EditRulesRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.NoDataResponse
import hous.release.data.entity.response.rule.OurRuleResponse
import hous.release.data.entity.response.rule.OurRulesResponse
import hous.release.data.service.OurRulesService
import hous.release.domain.entity.rule.OurRule
import javax.inject.Inject

class OurRulesDataSource @Inject constructor(private val ourRulesService: OurRulesService) {

    suspend fun getOurRulesContent(): BaseResponse<OurRulesResponse> =
        ourRulesService.getOurRuleContent()

    suspend fun postAddedRuleContent(addedRules: List<String>): Result<NoDataResponse> =
        runCatching { ourRulesService.postAddedRuleContent(AddRulesRequest(addedRules = addedRules)) }

    suspend fun putEditedRuleContent(editedRules: List<OurRule>): NoDataResponse =
        ourRulesService.putEditedRuleContent(
            EditRulesRequest(
                rules = editedRules.map { ourRule ->
                    OurRuleResponse(ourRule.id, ourRule.name)
                }
            )
        )

    suspend fun deleteRuleContent(deleteRules: List<Int>): NoDataResponse =
        ourRulesService.deleteRuleContent(DeleteRulesRequest(rulesIdList = deleteRules))
}
