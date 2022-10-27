package hous.release.domain.entity.response

data class Profile(
    val age: String = "",
    val birthday: String? = "",
    val birthdayPublic: Boolean = false,
    val introduction: String? = "",
    val job: String? = "",
    val mbti: String? = "",
    val nickname: String = "",
    val personalityColor: String = "",
    val representBadge: String? = "",
    val representBadgeImage: String? = "",
    val testScore: TestScore = TestScore()
) {
    data class TestScore(
        val clean: Int = 0,
        val introversion: Int = 0,
        val light: Int = 0,
        val noise: Int = 0,
        val smell: Int = 0
    )
}
