package hous.release.domain.entity.response

import hous.release.domain.entity.HomyType
import hous.release.domain.entity.TestScore

data class Profile(
    val age: String = "",
    val birthday: String = "",
    val birthdayPublic: Boolean = false,
    val introduction: String? = "",
    val job: String? = "",
    val mbti: String? = "",
    val nickname: String = "",
    val personalityColor: HomyType,
    val representBadge: String? = "",
    val representBadgeImage: String? = "",
    val testScore: TestScore = TestScore()
)
