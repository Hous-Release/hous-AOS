package hous.release.data.entity.response

import hous.release.domain.entity.HomyType
import hous.release.domain.entity.response.PersonalityResult

data class PersonalityResultResponse(
    val badPersonalityImageUrl: String,
    val badPersonalityName: String,
    val color: String,
    val description: List<String>,
    val goodPersonalityImageUrl: String,
    val goodPersonalityName: String,
    val imageUrl: String,
    val name: String,
    val recommendTitle: String,
    val recommendTodo: List<String>,
    val title: String
) {
    fun toPersonalityResult(): PersonalityResult = PersonalityResult(
        badPersonalityImageUrl = this.badPersonalityImageUrl,
        badPersonalityName = this.badPersonalityName,
        color = HomyType.valueOf(color),
        description = this.description,
        goodPersonalityImageUrl = this.goodPersonalityImageUrl,
        goodPersonalityName = this.goodPersonalityName,
        imageUrl = this.imageUrl,
        name = this.name,
        recommendTitle = this.recommendTitle,
        recommendTodo = this.recommendTodo,
        title = this.title
    )
}
