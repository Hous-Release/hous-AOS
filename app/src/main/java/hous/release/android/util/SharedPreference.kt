package hous.release.android.util

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreference @Inject constructor(@ApplicationContext context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE)

    fun setShowTutorial(showTutorial: Boolean) {
        prefs.edit().putBoolean(SHOW_TUTORIAL, showTutorial).apply()
    }

    fun getShowTutorial(): Boolean {
        return prefs.getBoolean(SHOW_TUTORIAL, false)
    }

    companion object {
        private const val SHOW_TUTORIAL = "ShowTutorial"
        private const val STORAGE_KEY = "StorageKey"
    }
}
