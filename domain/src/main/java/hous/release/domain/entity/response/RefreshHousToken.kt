package hous.release.domain.entity.response

import hous.release.domain.entity.Token

data class RefreshHousToken(
    val isJoiningRoom: Boolean,
    val token: Token
)
