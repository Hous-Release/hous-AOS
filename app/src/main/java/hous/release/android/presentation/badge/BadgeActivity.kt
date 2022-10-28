package hous.release.android.presentation.badge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BadgeActivity : ComponentActivity() {
    private val badgeViewModel: BadgeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                HousBadgeScreen(badgeViewModel = badgeViewModel) {
                    finish()
                }
            }
        }
        initBackPressedCallback()
    }

    private fun initBackPressedCallback() {
        onBackPressedDispatcher.addCallback {
            if (badgeViewModel.selectedBadgeIndex.value != -1) badgeViewModel.unLockBadges()
            else finish()
        }
    }
}
