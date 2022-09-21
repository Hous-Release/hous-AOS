package hous.release.data.entity.response

import hous.release.data.entity.TokenEntity
import hous.release.domain.entity.response.Login

data class LoginResponse(
    override val userId: String
) : Login
