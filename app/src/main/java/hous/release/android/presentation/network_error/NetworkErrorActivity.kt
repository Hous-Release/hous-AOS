package hous.release.android.presentation.network_error

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityNetworkErrorBinding
import hous.release.android.presentation.enter_room.EnterRoomActivity
import hous.release.android.presentation.login.LoginActivity
import hous.release.android.presentation.main.MainActivity
import hous.release.android.presentation.tutorial.TutorialActivity
import hous.release.android.util.ToastMessageUtil
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.extension.isNetworkConnected
import hous.release.android.util.extension.startActivity
import hous.release.domain.entity.SplashState
import hous.release.domain.usecase.GetSplashStateUseCase
import javax.inject.Inject

@AndroidEntryPoint
class NetworkErrorActivity :
    BindingActivity<ActivityNetworkErrorBinding>(R.layout.activity_network_error) {
    @Inject
    lateinit var getSplashStateUseCase: GetSplashStateUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNetworkConnect()
    }

    private fun checkNetworkConnect() {
        binding.btnNetworkErrorRetry.setOnClickListener {
            if (isNetworkConnected()) {
                when (getSplashStateUseCase()) {
                    SplashState.TUTORIAL -> startActivity<TutorialActivity>()
                    SplashState.LOGIN -> startActivity<LoginActivity>()
                    SplashState.ENTER_ROOM -> startActivity<EnterRoomActivity>()
                    SplashState.MAIN -> startActivity<MainActivity>()
                }
                overridePendingTransition(0, 0)
                finish()
            } else {
                ToastMessageUtil.showToast(this@NetworkErrorActivity, getString(R.string.network_error_toast))
            }
        }
    }
}
