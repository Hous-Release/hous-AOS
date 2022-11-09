package hous.release.domain.entity

data class PersonalityTest(
    val answers: List<String>,
    val imageUrl: String,
    val index: Int,
    val question: List<String>,
    val questionType: String
)
