package hous.release.domain.repository

import hous.release.domain.entity.response.BadgeContent

interface BadgeRepository {
    suspend fun getBadges(): Result<BadgeContent>
    suspend fun changeRepresentBadge(badgeId: Int)
}
