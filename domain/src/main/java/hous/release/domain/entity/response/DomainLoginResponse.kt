package hous.release.domain.entity.response

data class DomainLoginResponse(
    val token: Token,
    val userId: String
)
