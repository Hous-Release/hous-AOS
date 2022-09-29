package hous.release.data.entity

import hous.release.domain.entity.Token

data class TokenEntity(
    override val refreshToken: String,
    override val accessToken: String
) : Token
