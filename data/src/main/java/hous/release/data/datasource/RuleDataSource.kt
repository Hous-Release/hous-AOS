package hous.release.data.datasource

import hous.release.data.entity.request.AddRulesRequest
import hous.release.data.entity.request.DeleteRulesRequest
import hous.release.data.entity.request.EditRulesRequest
import hous.release.data.entity.response.NoDataResponse
import hous.release.data.entity.response.rule.DetailRuleResponse
import hous.release.data.entity.response.rule.MainRuleResponse
import hous.release.data.entity.response.rule.MainRulesResponse
import hous.release.data.service.RuleService
import hous.release.domain.entity.rule.MainRule
import javax.inject.Inject

class RuleDataSource @Inject constructor(private val ruleService: RuleService) {

    suspend fun fetchMainRules(): MainRulesResponse =
        ruleService.getMainRules().data

    suspend fun fetchDetailRuleBy(id: Int): DetailRuleResponse =
        ruleService.getDetailRuleBy(id).data
    suspend fun postAddedRuleContent(addedRules: List<String>): Result<NoDataResponse> =
        runCatching { ruleService.postAddedRuleContent(AddRulesRequest(addedRules = addedRules)) }

    suspend fun putEditedRuleContent(editedRules: List<MainRule>): NoDataResponse =
        ruleService.putEditedRuleContent(
            EditRulesRequest(
                rules = editedRules.map { ourRule ->
                    MainRuleResponse(ourRule.id, ourRule.name)
                }
            )
        )

    suspend fun deleteRuleContent(deleteRules: List<Int>): NoDataResponse =
        ruleService.deleteRuleContent(DeleteRulesRequest(rulesIdList = deleteRules))
}
