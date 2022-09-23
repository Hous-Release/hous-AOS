package hous.release.data.entity

import hous.release.data.entity.response.OurRulesResponse
import hous.release.domain.entity.OurRulesContent
import hous.release.domain.entity.response.OurRule

data class OurRulesEntity(
    override val ourRuleList: List<OurRule> = defaultRuleList,
    override val isEmptyRepresentativeRuleList: Boolean = true,
    override val isEmptyGeneralRuleList: Boolean = true,
    override val errorState: Boolean = true
) : OurRulesContent {
    companion object {
        val defaultRuleList = listOf(
            OurRulesResponse(),
            OurRulesResponse(),
            OurRulesResponse()
        )
    }
}
