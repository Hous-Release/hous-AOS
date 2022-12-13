package hous.release.android.presentation.enter_room

import android.os.Bundle
import androidx.activity.addCallback
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityEnterRoomBinding
import hous.release.android.util.ToastMessageUtil
import hous.release.android.util.binding.BindingActivity
import kotlin.system.exitProcess

@AndroidEntryPoint
class EnterRoomActivity : BindingActivity<ActivityEnterRoomBinding>(R.layout.activity_enter_room) {
    private var onBackPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBackPressedCallback()
    }

    private fun initBackPressedCallback() {
        onBackPressedDispatcher.addCallback {
            if (System.currentTimeMillis() - onBackPressedTime >= WAITING_DEADLINE) {
                onBackPressedTime = System.currentTimeMillis()
                ToastMessageUtil.showToast(
                    this@EnterRoomActivity,
                    getString(R.string.finish_app_toast_msg)
                )
            } else {
                finishAffinity()
                System.runFinalization()
                exitProcess(0)
            }
        }
    }

    companion object {
        private const val WAITING_DEADLINE = 2000L
    }
}
