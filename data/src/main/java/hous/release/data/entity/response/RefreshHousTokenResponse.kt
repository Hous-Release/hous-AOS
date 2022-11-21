package hous.release.data.entity.response

import hous.release.data.entity.TokenEntity
import hous.release.domain.entity.Token
import hous.release.domain.entity.response.RefreshHousToken

data class RefreshHousTokenResponse(
    val isJoiningRoom: Boolean,
    val token: TokenEntity
) {
    fun toRefreshHousToken(): RefreshHousToken =
        RefreshHousToken(
            isJoiningRoom = this.isJoiningRoom,
            token = Token(
                accessToken = this.token.accessToken,
                refreshToken = this.token.refreshToken
            )
        )
}
