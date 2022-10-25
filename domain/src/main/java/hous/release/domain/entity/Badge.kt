package hous.release.domain.entity

data class Badge(
    val badgeId: Int,
    val description: String,
    val imageUrl: String,
    val isAcquired: Boolean,
    val isRead: Boolean,
    val name: String,
    val badgeState: BadgeState = BadgeState.LOCK
)

enum class BadgeState {
    UNLOCK, LOCK, CHECKED
}
