package hous.release.data.entity.request

import hous.release.data.entity.response.rule.OurRuleResponse

data class EditRulesRequest(
    val rules: List<OurRuleResponse> = emptyList()
)
