package hous.release.data.entity.response.rule

import hous.release.domain.entity.rule.OurRule
import hous.release.domain.entity.rule.type.RuleType

data class OurRulesResponse(
    val rules: List<OurRuleResponse> = emptyList()
)

data class OurRuleResponse(
    val id: Int = -1,
    val name: String = "",
    val createdAt: String = "",
    val isNew: Boolean = false
) {
    // TODO : 다음 버전에서 삭제
    fun toOurRule() = OurRule(
        id = id,
        name = name,
        ruleType = RuleType.GENERAL
    )
}
