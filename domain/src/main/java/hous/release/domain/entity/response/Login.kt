package hous.release.domain.entity.response

data class Login(
    val isJoiningRoom: Boolean = false,
    val token: Token = Token(),
    val userId: String = ""
) {
    data class Token(
        val accessToken: String = "",
        val refreshToken: String = ""
    )
}
