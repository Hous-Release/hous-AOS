package hous.release.android.presentation.splash

import android.animation.Animator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityIntroBinding
import hous.release.android.presentation.enter_room.EnterRoomActivity
import hous.release.android.presentation.force_update.ForceUpdateDialogFragment
import hous.release.android.presentation.login.LoginActivity
import hous.release.android.presentation.main.MainActivity
import hous.release.android.presentation.tutorial.TutorialActivity
import hous.release.android.util.HousLogEvent.SCREEN_HOME
import hous.release.android.util.HousLogEvent.enterScreenLogEvent
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.dialog.ConfirmClickListener
import hous.release.android.util.extension.repeatOnStarted
import hous.release.android.util.extension.startActivity
import hous.release.domain.entity.SplashState

@AndroidEntryPoint
class IntroActivity : BindingActivity<ActivityIntroBinding>(R.layout.activity_intro) {
    private val viewModel by viewModels<IntroViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getVersionCheck()
        initLottieAnimatorListener()
        initVersionInfoCollector()
        initIsSuccessUiEventCollector()
    }

    private fun initLottieAnimatorListener() {
        binding.lottieSplashImg.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {
                viewModel.initIsAnimatorDone()
            }

            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })
    }

    private fun initVersionInfoCollector() {
        repeatOnStarted {
            viewModel.versionCheck.collect { versionInfo ->
                if (versionInfo.needsForceUpdate) {
                    ForceUpdateDialogFragment().apply {
                        arguments = Bundle().apply {
                            putParcelable(
                                ForceUpdateDialogFragment.CONFIRM_ACTION,
                                ConfirmClickListener(
                                    confirmAction = {
                                        startActivity(
                                            Intent(
                                                Intent.ACTION_VIEW,
                                                Uri.parse(versionInfo.marketUrl)
                                            )
                                        )
                                    }
                                )
                            )
                        }
                    }.show(supportFragmentManager, ForceUpdateDialogFragment().javaClass.name)
                }
            }
        }
    }

    private fun initIsSuccessUiEventCollector() {
        repeatOnStarted {
            viewModel.isSuccessUiEvent.collect { isSuccess ->
                if (isSuccess) {
                    when (viewModel.getSplashState()) {
                        SplashState.TUTORIAL -> startActivity<TutorialActivity>()
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
        }
    }

    companion object {
        private const val MAIN_ACTIVITY_TAG = "MainActivity"
    }
}
