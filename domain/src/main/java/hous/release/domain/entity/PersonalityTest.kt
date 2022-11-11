package hous.release.domain.entity

data class PersonalityTest(
    val answers: List<String>,
    val imageUrl: String,
    val index: Int,
    val question: String,
    val questionType: QuestionType,
    val testState: TestState = TestState.UNCHECKED
)

enum class TestState {
    UNCHECKED, ONE, TWO, THREE
}

enum class QuestionType {
    LIGHT, NOISE, CLEAN, SMELL, INTROVERSION
}
