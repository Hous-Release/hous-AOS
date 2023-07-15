package hous.release.data.datasource

import hous.release.data.entity.request.AddRulesRequest
import hous.release.data.entity.request.DeleteRulesRequest
import hous.release.data.entity.request.EditRulesRequest
import hous.release.data.entity.response.NoDataResponse
import hous.release.data.entity.response.rule.MainRuleResponse
import hous.release.data.entity.response.rule.MainRulesResponse
import hous.release.data.service.OurRulesService
import hous.release.domain.entity.rule.OurRule
import javax.inject.Inject

class OurRulesDataSource @Inject constructor(private val ourRulesService: OurRulesService) {

    suspend fun fetchMainRules(): MainRulesResponse =
        ourRulesService.getOurRuleContent().data

    suspend fun postAddedRuleContent(addedRules: List<String>): NoDataResponse =
        ourRulesService.postAddedRuleContent(AddRulesRequest(addedRules = addedRules))

    suspend fun putEditedRuleContent(editedRules: List<OurRule>): NoDataResponse =
        ourRulesService.putEditedRuleContent(
            EditRulesRequest(
                rules = editedRules.map { ourRule ->
                    MainRuleResponse(ourRule.id, ourRule.name)
                }
            )
        )

    suspend fun deleteRuleContent(deleteRules: List<Int>): NoDataResponse =
        ourRulesService.deleteRuleContent(DeleteRulesRequest(rulesIdList = deleteRules))
}
