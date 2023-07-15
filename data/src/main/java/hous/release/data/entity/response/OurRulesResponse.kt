package hous.release.data.entity.response

import hous.release.domain.entity.rule.type.RuleType
import hous.release.domain.entity.rule.OurRule

data class OurRulesResponse(
    val rules: List<OurRuleResponse> = emptyList()
)

data class OurRuleResponse(
    val id: Int = -1,
    val name: String = ""
) {
    // TODO : 다음 버전에서 삭제
    fun toOurRule() = OurRule(
        id = id,
        name = name,
        ruleType = RuleType.GENERAL
    )
}
