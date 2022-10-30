package hous.release.data.datasource

import hous.release.data.entity.response.BadgeResponse
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.NoDataResponse
import hous.release.data.service.BadgeService
import javax.inject.Inject

class BadgeDataSource @Inject constructor(
    private val badgeService: BadgeService
) {
    suspend fun getBadges(): BaseResponse<BadgeResponse> = badgeService.getBadges()
    suspend fun changeRepresentBadge(badgeId: Int): NoDataResponse =
        badgeService.changeRepresentBadge(badgeId)
}
