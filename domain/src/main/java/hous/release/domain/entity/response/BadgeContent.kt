package hous.release.domain.entity.response

import hous.release.domain.entity.Badge

data class BadgeContent(
    val representBadge: Badge?,
    val badges: List<Badge>
)
