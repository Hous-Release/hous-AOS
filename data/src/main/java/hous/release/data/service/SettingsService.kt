package hous.release.data.service

import hous.release.data.entity.request.NotificationSettingsRequest
import hous.release.data.entity.response.NoDataResponse
import retrofit2.http.Body
import retrofit2.http.PATCH

interface SettingsService {
    @PATCH
    suspend fun patchNotificationSettings(
        @Body body: NotificationSettingsRequest
    ): NoDataResponse
}
