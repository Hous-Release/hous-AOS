package hous.release.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import hous.release.data.datasource.NotificationDataSource
import hous.release.data.datasource.NotificationPagingSource
import hous.release.domain.entity.response.NotificationContent
import hous.release.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSource: NotificationDataSource
) : NotificationRepository {
    override fun getNotification(
        size: Int
    ): Flow<PagingData<NotificationContent>> =
        Pager(
            config = PagingConfig(pageSize = size, enablePlaceholders = false),
            pagingSourceFactory = {
                NotificationPagingSource(notificationDataSource, size)
            }
        ).flow
}
