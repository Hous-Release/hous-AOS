package hous.release.android.presentation.splash

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityIntroBinding
import hous.release.android.presentation.enter_room.EnterRoomActivity
import hous.release.android.presentation.login.LoginActivity
import hous.release.android.presentation.main.MainActivity
import hous.release.android.presentation.tutorial.TutorialActivity
import hous.release.android.util.HousLogEvent.SCREEN_HOME
import hous.release.android.util.HousLogEvent.enterScreenLogEvent
import hous.release.android.util.HousLogEvent.openAppEvent
import hous.release.android.util.binding.BindingActivity
import hous.release.domain.entity.SplashState
import hous.release.domain.usecase.GetSplashStateUseCase
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class IntroActivity : BindingActivity<ActivityIntroBinding>(R.layout.activity_intro) {
    @Inject
    lateinit var getSplashStateUseCase: GetSplashStateUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lottieSplashImg.playAnimation()
        lifecycleScope.launch {
            delay(3000)
            when (getSplashStateUseCase()) {
                SplashState.TUTORIAL -> {
                    openAppEvent()
                    startActivity<TutorialActivity>()
                }
                SplashState.LOGIN -> startActivity<LoginActivity>()
                SplashState.ENTER_ROOM -> startActivity<EnterRoomActivity>()
                SplashState.MAIN -> {
                    enterScreenLogEvent(SCREEN_HOME, MAIN_ACTIVITY_TAG)
                    startActivity<MainActivity>()
                }
            }
            overridePendingTransition(0, 0)
            finish()
        }
    }

    inline fun <reified T : Activity> Context.startActivity(block: Intent.() -> Unit = {}) {
        startActivity(Intent(this, T::class.java).apply(block))
    }

    companion object {
        private const val MAIN_ACTIVITY_TAG = "MainActivity"
    }
}
