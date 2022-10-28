package hous.release.data.entity

import hous.release.domain.entity.Badge
import hous.release.domain.entity.BadgeState

data class BadgeEntity(
    val badgeId: Int,
    val description: String,
    val imageUrl: String,
    val isAcquired: Boolean,
    val isRead: Boolean,
    val name: String
) {
    fun toBadge(representBadge: BadgeEntity?) = Badge(
        badgeId = badgeId,
        description = description,
        imageUrl = imageUrl,
        isAcquired = isAcquired,
        isRead = isRead,
        name = name,
        badgeState =
        if (isAcquired) BadgeState.UNLOCK
        else if (representBadge != null && representBadge.badgeId == badgeId) BadgeState.REPRESENT
        else BadgeState.LOCK
    )
}
