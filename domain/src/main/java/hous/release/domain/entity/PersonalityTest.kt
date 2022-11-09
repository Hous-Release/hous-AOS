package hous.release.domain.entity

data class PersonalityTest(
    val answers: List<String>,
    val imageUrl: String,
    val index: Int,
    val question: List<String>,
    val questionType: String,
    val testState: TestState = TestState.UNCHECKED
)

enum class TestState {
    UNCHECKED, ONE, TWO, THREE
}
