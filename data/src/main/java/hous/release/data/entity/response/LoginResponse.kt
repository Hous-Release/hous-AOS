package hous.release.data.entity.response

import hous.release.data.entity.TokenEntity
import hous.release.domain.entity.response.Login

data class LoginResponse(
    val isJoiningRoom: Boolean,
    val token: TokenEntity,
    val userId: String
) {
    fun toLogin(): Login = Login(
        isJoiningRoom = this.isJoiningRoom,
        token = this.token.toToken(),
        userId = this.userId
    )
}
