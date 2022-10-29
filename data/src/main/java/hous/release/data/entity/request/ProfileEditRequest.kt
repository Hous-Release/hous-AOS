package hous.release.data.entity.request

data class ProfileEditRequest(
    val birthday: String,
    val introduction: String,
    val isPublic: Boolean,
    val job: String,
    val mbti: String,
    val nickname: String
)
