package hous.release.data.entity.response

import hous.release.data.entity.TokenEntity
import hous.release.domain.entity.response.SignUp

data class SignUpResponse(
    val isJoiningRoom: Boolean,
    val token: TokenEntity,
    val userId: String
) {
    fun toSignUp(): SignUp =
        SignUp(
            isJoiningRoom = this.isJoiningRoom,
            token = this.token.toToken(),
            userId = this.userId
        )
}
