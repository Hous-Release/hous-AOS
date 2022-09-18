package hous.release.data.entity.response

data class LoginResponse(
    val token: Token,
    val userId: String
)

data class Token(
    val accessToken: String,
    val refreshToken: String
)
