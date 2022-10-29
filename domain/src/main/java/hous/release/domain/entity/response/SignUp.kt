package hous.release.domain.entity.response

import hous.release.domain.entity.Token

data class SignUp(
    val isJoiningRoom: Boolean = false,
    val token: Token = Token(),
    val userId: String = ""
)
