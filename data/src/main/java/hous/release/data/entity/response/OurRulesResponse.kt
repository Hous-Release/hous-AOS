package hous.release.data.entity.response

import hous.release.domain.entity.response.OurRule

data class OurRulesResponse(
    override val id: Int = -1,
    override val name: String = ""
) : OurRule
