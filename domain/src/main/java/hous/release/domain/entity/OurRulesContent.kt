package hous.release.domain.entity

import hous.release.domain.entity.response.OurRule

data class OurRulesContent(
    val ourRuleList: List<OurRule> = defaultRuleList,
    val isEmptyRepresentativeRuleList: Boolean = true,
    val isEmptyGeneralRuleList: Boolean = true,
    val errorMsg: String? = ""
) {
    companion object {
        val defaultRuleList = listOf(
            OurRule(),
            OurRule(),
            OurRule()
        )
    }
}
