package hous.release.domain.entity.response

import hous.release.domain.entity.HomyType

data class PersonalityResult(
    val badPersonalityImageUrl: String = "",
    val badPersonalityName: String = "",
    val color: HomyType = HomyType.GRAY,
    val firstDownloadImageUrl: String = "",
    val description: String = "",
    val goodPersonalityImageUrl: String = "",
    val goodPersonalityName: String = "",
    val imageUrl: String = "",
    val name: String = "",
    val recommendTitle: String = "",
    val recommendTodo: List<String> = emptyList(),
    val secondDownloadImageUrl: String = "",
    val title: String = ""
)
