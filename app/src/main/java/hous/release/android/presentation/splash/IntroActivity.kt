package hous.release.android.presentation.splash

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
import hous.release.android.util.binding.BindingActivity
import hous.release.domain.usecase.GetEnterRoomInfoUseCase
import hous.release.domain.usecase.GetIsAccessTokenUseCase
import hous.release.domain.usecase.GetSkipTutorialUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class IntroActivity : BindingActivity<ActivityIntroBinding>(R.layout.activity_intro) {
    @Inject
    lateinit var getSkipTutorialUseCase: GetSkipTutorialUseCase

    @Inject
    lateinit var getIsAccessTokenUseCase: GetIsAccessTokenUseCase

    @Inject
    lateinit var getEnterRoomInfoUseCase: GetEnterRoomInfoUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lottieSplashImg.playAnimation()
        lifecycleScope.launch {
            delay(3000)
            if (getIsAccessTokenUseCase()) {
                getEnterRoomInfoUseCase()
                    .onSuccess {
                        startActivity(
                            Intent(this@IntroActivity, MainActivity::class.java)
                        )
                    }
                    .onFailure {
                        startActivity(
                            Intent(this@IntroActivity, EnterRoomActivity::class.java)
                        )
                    }
            } else {
                if (getSkipTutorialUseCase()) startActivity(
                    Intent(this@IntroActivity, LoginActivity::class.java)
                )
                else startActivity(Intent(this@IntroActivity, TutorialActivity::class.java))
            }
            overridePendingTransition(0, 0)
            finish()
        }
    }
}
