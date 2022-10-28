package hous.release.data.entity.response

import hous.release.data.entity.BadgeEntity
import hous.release.domain.entity.response.BadgeContent

data class BadgeResponse(
    val representBadge: BadgeEntity?,
    val badges: List<BadgeEntity>
) {
    fun toBadgeContent() = BadgeContent(
        representBadge = representBadge.let { it?.toBadge(it) },
        badges = badges
            .map { badgeEntity -> badgeEntity.toBadge(representBadge) }
    )
}
