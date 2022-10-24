package hous.release.data.entity.response

import hous.release.domain.entity.RuleType
import hous.release.domain.entity.response.OurRule

data class OurRulesResponse(
    val id: Int = -1,
    val name: String = ""
) {
    fun toOurRule() = OurRule().copy(
        id = id,
        name = name,
        ruleType = RuleType.GENERAL
    )
}
