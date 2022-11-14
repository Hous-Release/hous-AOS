package hous.release.data.entity.request

import hous.release.data.entity.response.OurRuleResponse

data class EditRulesRequest(
    val rules: List<OurRuleResponse> = emptyList()
)
