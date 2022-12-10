package hous.release.android.presentation.splash

import android.animation.Animator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityIntroBinding
import hous.release.android.presentation.enter_room.EnterRoomActivity
import hous.release.android.presentation.login.LoginActivity
import hous.release.android.presentation.main.MainActivity
import hous.release.android.presentation.tutorial.TutorialActivity
import hous.release.android.util.binding.BindingActivity
import hous.release.domain.entity.SplashState
import hous.release.domain.usecase.GetSplashStateUseCase
import javax.inject.Inject

@AndroidEntryPoint
class IntroActivity : BindingActivity<ActivityIntroBinding>(R.layout.activity_intro) {
    @Inject
    lateinit var getSplashStateUseCase: GetSplashStateUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lottieSplashImg.playAnimation()
        binding.lottieSplashImg.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {
                when (getSplashStateUseCase()) {
                    SplashState.TUTORIAL -> startActivity<TutorialActivity>()
                    SplashState.LOGIN -> startActivity<LoginActivity>()
                    SplashState.ENTER_ROOM -> startActivity<EnterRoomActivity>()
                    SplashState.MAIN -> startActivity<MainActivity>()
                }
                overridePendingTransition(0, 0)
                finish()
            }

            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })
    }

    inline fun <reified T : Activity> Context.startActivity(block: Intent.() -> Unit = {}) {
        startActivity(Intent(this, T::class.java).apply(block))
    }
}
