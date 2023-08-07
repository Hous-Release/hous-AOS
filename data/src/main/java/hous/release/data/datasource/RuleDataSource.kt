package hous.release.data.datasource

import hous.release.data.entity.request.DeleteRulesRequest
import hous.release.data.entity.request.EditRulesRequest
import hous.release.data.entity.request.rule.AddRulesRequest
import hous.release.data.entity.response.NoDataResponse
import hous.release.data.entity.response.rule.DetailRuleResponse
import hous.release.data.entity.response.rule.MainRuleResponse
import hous.release.data.entity.response.rule.MainRulesResponse
import hous.release.data.service.RuleService
import hous.release.data.util.multipart.toImagePart
import hous.release.domain.entity.rule.MainRule
import javax.inject.Inject

class RuleDataSource @Inject constructor(private val ruleService: RuleService) {

    suspend fun fetchMainRules(): MainRulesResponse =
        ruleService.getMainRules().data

    suspend fun fetchDetailRuleBy(id: Int): DetailRuleResponse =
        ruleService.getDetailRuleBy(id).data

    suspend fun canAddRule(): Boolean =
        ruleService.getAddableRules().data.isAddable

    suspend fun addRule(req: AddRulesRequest) =
        if (req.imageFiles.isEmpty()) {
            ruleService.addRuleNoImage(
                description = req.description,
                name = req.name
            )
        } else {
            ruleService.addRule(
                description = req.description,
                name = req.name,
                images = req.imageFiles.map { file -> file.toImagePart() }
            )
        }

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
