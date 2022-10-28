package hous.release.domain.repository

import androidx.paging.PagingData
import hous.release.domain.entity.response.NotificationContent
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getNotification(
        size: Int
    ): Flow<PagingData<NotificationContent>>
}
