package hous.release.data.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class LocalPrefTokenDataSource @Inject constructor(
    private val prefs: SharedPreferences
) {
    var accessToken: String
        set(value) = prefs.edit { putString(ACCESS_TOKEN, value) }
        get() = prefs.getString(ACCESS_TOKEN, "") ?: ""

    var refreshToken: String
        set(value) = prefs.edit { putString(REFRESH_TOKEN, value) }
        get() = prefs.getString(REFRESH_TOKEN, "") ?: ""

    var fcmToken: String
        set(value) = prefs.edit { putString(FCM_TOKEN, value) }
        get() = prefs.getString(FCM_TOKEN, "") ?: ""

    var socialType: String
        set(value) = prefs.edit { putString(SOCIAL_TYPE, value) }
        get() = prefs.getString(SOCIAL_TYPE, "") ?: ""

    var token: String
        set(value) = prefs.edit { putString(TOKEN, value) }
        get() = prefs.getString(TOKEN, "") ?: ""

    companion object {
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        private const val FCM_TOKEN = "fcm_token"
        private const val SOCIAL_TYPE = "social_type"
        private const val TOKEN = "token"
    }
}
