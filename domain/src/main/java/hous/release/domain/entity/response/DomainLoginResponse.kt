package hous.release.domain.entity.response

import hous.release.domain.entity.Token

data class DomainLoginResponse(
    val isJoiningRoom: Boolean,
    val token: Token,
    val userId: String
)
