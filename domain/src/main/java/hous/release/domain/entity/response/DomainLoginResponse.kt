package hous.release.domain.entity.response

data class DomainLoginResponse(
    val isJoiningRoom: Boolean,
    val token: Token,
    val userId: String
)

data class Token(
    val accessToken: String,
    val refreshToken: String
)
