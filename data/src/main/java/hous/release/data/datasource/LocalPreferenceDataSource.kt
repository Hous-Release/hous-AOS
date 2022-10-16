package hous.release.data.datasource

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalPreferenceDataSource @Inject constructor(@ApplicationContext context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE)

    fun initShowTutorial(skipTutorial: Boolean) {
        prefs.edit().putBoolean(LocalPreferenceDataSource.SKIP_TUTORIAL, skipTutorial).apply()
    }

    fun getShowTutorial(): Boolean {
        return prefs.getBoolean(SKIP_TUTORIAL, false)
    }

    companion object {
        private const val SKIP_TUTORIAL = "SkipTutorial"
        private const val STORAGE_KEY = "StorageKey"
    }
}
