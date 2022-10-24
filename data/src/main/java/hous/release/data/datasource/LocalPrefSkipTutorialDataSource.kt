package hous.release.data.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class LocalPrefSkipTutorialDataSource @Inject constructor(
    private val prefs: SharedPreferences
) {
    var showTutorial: Boolean
        set(value) = prefs.edit { putBoolean(SKIP_TUTORIAL, value) }
        get() = prefs.getBoolean(SKIP_TUTORIAL, false)

    companion object {
        private const val SKIP_TUTORIAL = "SkipTutorial"
    }
}
