package hous.release.data.entity.response.rule

import hous.release.domain.entity.rule.MainRule

data class MainRulesResponse(
    val rules: List<MainRuleResponse> = emptyList()
)

data class MainRuleResponse(
    val id: Int = -1,
    val name: String = "",
    val createdAt: String = "",
    val isNew: Boolean = false
) {
    fun toMainRule() = MainRule(
        id = id,
        name = name,
        createdAt = createdAt.substringBefore('T').replace('-', '.'),
        isNew = isNew
    )
}
