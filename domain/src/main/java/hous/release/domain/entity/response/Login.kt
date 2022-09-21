package hous.release.domain.entity.response

import hous.release.domain.entity.Token

interface Login {
    val token: List<Token>
    val userId: String
}
