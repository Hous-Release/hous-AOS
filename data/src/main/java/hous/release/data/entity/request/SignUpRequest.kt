package hous.release.data.entity.request

data class SignUpRequest(
    val birthday: String,
    val fcmToken: String,
    val isPublic: Boolean,
    val nickname: String,
    val socialType: String,
    val token: String
)
