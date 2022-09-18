package hous.release.data.entity.request

data class LoginRequest(
    val fcmToken: String,
    val socialType: String,
    val token: String
)
