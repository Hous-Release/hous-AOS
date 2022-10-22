package hous.release.data.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class LocalPrefTokenDataSource @Inject constructor(
    private val prefs: SharedPreferences
) {
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
        private const val FCM_TOKEN = "fcm token"
        private const val SOCIAL_TYPE = "social type"
        private const val TOKEN = "token"
    }
}
