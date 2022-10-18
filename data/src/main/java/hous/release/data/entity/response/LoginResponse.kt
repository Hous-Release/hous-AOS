package hous.release.data.entity.response

import hous.release.domain.entity.response.Login

data class LoginResponse(
    val isJoiningRoom: Boolean,
    val token: Login.Token,
    val userId: String
)