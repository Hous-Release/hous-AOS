package hous.release.data.repository

import hous.release.data.datasource.BadgeDataSource
import hous.release.domain.entity.response.BadgeContent
import hous.release.domain.repository.BadgeRepository
import javax.inject.Inject

class BadgeRepositoryImpl @Inject constructor(
    private val badgeDataSource: BadgeDataSource
) : BadgeRepository {
    override suspend fun getBadges(): Result<BadgeContent> = runCatching {
        badgeDataSource.getBadges().data.toBadgeContent()
    }
}
