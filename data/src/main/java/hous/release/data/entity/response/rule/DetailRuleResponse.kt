package hous.release.data.entity.response.rule

import hous.release.domain.entity.rule.DetailRule

data class DetailRuleResponse(
    val id: Int = -1,
    val name: String = "",
    val description: String = "",
    val images: List<String> = emptyList(),
    val updatedAt: String = ""
) {
    fun toDetailRule() = DetailRule(
        id = id,
        name = name,
        description = description,
        images = images,
        updatedAt = updatedAt.substringBefore('T').replace('-', '.')
    )
}
