package hous.release.data.model.request

data class LoginRequest(
    val fcmToken: String,
    val socialType: String,
    val token: String
)
