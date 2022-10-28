package hous.release.data.entity.response

import hous.release.data.entity.TestScoreEntity
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.response.Profile

data class ProfileResponse(
    val age: String,
    val birthday: String?,
    val birthdayPublic: Boolean,
    val introduction: String?,
    val job: String?,
    val mbti: String?,
    val nickname: String,
    val personalityColor: String,
    val representBadge: String?,
    val representBadgeImage: String?,
    val testScore: TestScoreEntity

) {
    fun toProfile(): Profile = Profile(
        age = this.age,
        birthday = this.birthday,
        birthdayPublic = this.birthdayPublic,
        introduction = this.introduction,
        job = this.job,
        mbti = this.mbti,
        nickname = this.nickname,
        personalityColor = HomyType.valueOf(personalityColor),
        representBadge = this.representBadge,
        representBadgeImage = this.representBadgeImage,
        testScore = this.testScore.toTestScore()
    )
}
