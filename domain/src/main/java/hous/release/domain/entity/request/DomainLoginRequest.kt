package hous.release.domain.entity.request

data class DomainLoginRequest(
    val fcmToken: String,
    val socialType: String,
    val token: String
)
