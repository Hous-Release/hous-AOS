package hous.release.data.entity

import hous.release.domain.entity.Token

data class TokenEntity(
    val accessToken: String,
    val refreshToken: String
) {
    fun toToken() = Token(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken
    )
}
