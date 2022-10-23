package hous.release.data.entity.response

import hous.release.domain.entity.response.SignUp

data class SignUpResponse(
    val isJoiningRoom: Boolean,
    val token: SignUp.Token,
    val userId: String
) {
    fun toSignUp(): SignUp =
        SignUp(
            isJoiningRoom = this.isJoiningRoom,
            token = this.token,
            userId = this.userId
        )
}
