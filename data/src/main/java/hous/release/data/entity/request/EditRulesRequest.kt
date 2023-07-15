package hous.release.data.entity.request

import hous.release.data.entity.response.rule.MainRuleResponse

data class EditRulesRequest(
    val rules: List<MainRuleResponse> = emptyList()
)
