package hous.release.android.presentation.badge

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R

@AndroidEntryPoint
class BadgeActivity : ComponentActivity() {
    private val badgeViewModel: BadgeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {
            HousBadgeScreen(badgeViewModel = badgeViewModel) {
                finish()
            }
        }
        initStatusBarColor()
    }

    private fun initStatusBarColor() {
        window?.statusBarColor = ContextCompat.getColor(this, R.color.hous_g_7)
    }
}
