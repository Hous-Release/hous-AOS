package hous.release.data.entity.response

import hous.release.domain.entity.HomyType
import hous.release.domain.entity.response.PersonalityResult

data class PersonalityResultResponse(
    val badPersonalityImageUrl: String,
    val badPersonalityName: String,
    val color: String,
    val firstDownloadImageUrl: String,
    val description: List<String>,
    val goodPersonalityImageUrl: String,
    val goodPersonalityName: String,
    val imageUrl: String,
    val name: String,
    val recommendTitle: String,
    val recommendTodo: List<String>,
    val secondDownloadImageUrl: String,
    val title: String
) {
    fun toPersonalityResult(): PersonalityResult = PersonalityResult(
        badPersonalityImageUrl = this.badPersonalityImageUrl,
        badPersonalityName = this.badPersonalityName,
        color = HomyType.valueOf(color),
        firstDownloadImageUrl = this.firstDownloadImageUrl,
        description = this.description.joinToString(ENTER),
        goodPersonalityImageUrl = this.goodPersonalityImageUrl,
        goodPersonalityName = this.goodPersonalityName,
        imageUrl = this.imageUrl,
        name = this.name,
        recommendTitle = this.recommendTitle,
        recommendTodo = this.recommendTodo,
        secondDownloadImageUrl = this.secondDownloadImageUrl,
        title = this.title
    )

    companion object {
        private const val ENTER = "\n"
    }
}
