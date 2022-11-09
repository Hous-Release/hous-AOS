package hous.release.data.entity

import hous.release.domain.entity.PersonalityTest
import hous.release.domain.entity.QuestionType

data class PersonalityTestEntity(
    val answers: List<String>,
    val imageUrl: String,
    val index: Int,
    val question: List<String>,
    val questionType: String
) {
    fun toPersonalityTest() = PersonalityTest(
        answers = answers,
        imageUrl = imageUrl,
        index = index,
        question = question,
        questionType = QuestionType.valueOf(questionType)
    )
}
