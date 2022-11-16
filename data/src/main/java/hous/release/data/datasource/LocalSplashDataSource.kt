package hous.release.data.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class LocalSplashDataSource @Inject constructor(
    private val prefs: SharedPreferences
) {
    var splashState: String
        set(value) = prefs.edit { putString(SPLASH_STATE, value) }
        get() = prefs.getString(SPLASH_STATE, DEFAULT_STATE) ?: DEFAULT_STATE

    companion object {
        private const val SPLASH_STATE = "splash_state"
        private const val DEFAULT_STATE = "TUTORIAL"
    }
}
