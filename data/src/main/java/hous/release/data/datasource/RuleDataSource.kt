package hous.release.data.datasource

import hous.release.data.entity.request.rule.AddRulesRequest
import hous.release.data.entity.request.rule.UpdateRuleRequest
import hous.release.data.entity.response.NoDataResponse
import hous.release.data.entity.response.rule.DetailRuleResponse
import hous.release.data.entity.response.rule.RulesResponse
import hous.release.data.service.RuleService
import hous.release.data.util.multipart.toImagePart
import javax.inject.Inject

class RuleDataSource @Inject constructor(private val ruleService: RuleService) {

    suspend fun fetchRules(): RulesResponse =
        ruleService.getRules().data

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

    suspend fun updateRule(req: UpdateRuleRequest): NoDataResponse =
        if (req.imageFiles.isEmpty()) {
            ruleService.updateRuleNoImage(
                id = req.id,
                description = req.description,
                name = req.name
            )
        } else {
            ruleService.updateRule(
                id = req.id,
                description = req.description,
                name = req.name,
                images = req.imageFiles.map { file -> file.toImagePart() }
            )
        }

    suspend fun deleteRule(ruleId: Int): NoDataResponse =
        ruleService.deleteRule(ruleId)
}
