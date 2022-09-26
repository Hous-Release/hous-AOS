package hous.release.domain.entity

import hous.release.domain.entity.response.OurRule

interface OurRulesContent {
    val ourRuleList: List<OurRule>
    val isEmptyRepresentativeRuleList: Boolean
    val isEmptyGeneralRuleList: Boolean
    val errorState: Boolean
}
