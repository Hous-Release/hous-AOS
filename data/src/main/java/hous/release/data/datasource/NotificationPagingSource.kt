package hous.release.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import hous.release.domain.entity.response.NotificationContent
import timber.log.Timber

class NotificationPagingSource(
    private val notificationDataSource: NotificationDataSource,
    private val size: Int = 10
) : PagingSource<Int, NotificationContent>() {
    private var currentIdKey: Int = 1
    private val lastIdMap = hashMapOf<Int, Int>()

    init {
        initFirstId()
    }

    private fun initFirstId() {
        lastIdMap[1] = FIRST_ID
    }

    override fun getRefreshKey(state: PagingState<Int, NotificationContent>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            when {
                anchorPage?.prevKey != null -> {
                    lastIdMap[currentIdKey + 1]
                }
                anchorPage?.nextKey != null -> {
                    lastIdMap[currentIdKey - 1]
                }
                else -> {
                    null
                }
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NotificationContent> {
        return try {
            val idKey = currentIdKey++
            val lastId = params.key ?: Int.MAX_VALUE
            val notificationData =
                notificationDataSource.getNotification(lastId, size).data.toNotification()
            val notificationList = notificationData.contents
            if (notificationData.nextCursor != -1) {
                lastIdMap[idKey + 1] = notificationData.nextCursor
            }
            return LoadResult.Page(
                data = notificationList,
                prevKey = if (idKey <= 1) null else lastIdMap[idKey - 1],
                nextKey = if (notificationData.nextCursor == -1) null else lastIdMap[idKey + 1]
            )
        } catch (e: Exception) {
            Timber.tag(this.javaClass.toString()).d(e.message.toString())
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_ID = Int.MAX_VALUE
    }
}
