package hous.release.data.datasource

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPrefDataSource @Inject constructor(@ApplicationContext context: Context) {
    val prefs: SharedPreferences =
        context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE)

    companion object {
        private const val STORAGE_KEY = "StorageKey"
    }
}
