package hous.release.android.presentation.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivitySettingsBinding
import hous.release.android.presentation.login.LoginActivity
import hous.release.android.presentation.out_room.OutRoomActivity
import hous.release.android.presentation.withdraw.WithdrawActivity
import hous.release.android.util.HousLogEvent.CLICK_LOG_OUT
import hous.release.android.util.HousLogEvent.clickDateLogEvent
import hous.release.android.util.ToastMessageUtil
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.dialog.ConfirmClickListener
import hous.release.android.util.dialog.WarningDialogFragment
import hous.release.android.util.dialog.WarningType
import hous.release.android.util.extension.repeatOnStarted

@AndroidEntryPoint
class SettingsActivity : BindingActivity<ActivitySettingsBinding>(R.layout.activity_settings) {
    private val viewModel by viewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSettingsMode()
        initBackClickListener()
        initNotiSettingClickListener()
        initInfoClickListener()
        initOpensourceClickListener()
        initFeedbackClickListener()
        initOutRoomClickListener()
        initWithdrawClickListener()
        initLogoutClickListener()
        initIsSuccessLogoutCollector()
        initIsAllowedLogoutCollector()
    }

    private fun initSettingsMode() {
        binding.hasRoom = intent.getBooleanExtra(HAS_ROOM, true)
    }

    private fun initBackClickListener() {
        binding.btnSettingsBack.setOnClickListener { finish() }
    }

    private fun initNotiSettingClickListener() {
        binding.tvSettingsNotification.setOnClickListener {
            startActivity(Intent(this, NotificationSettingsActivity::class.java))
        }
    }

    private fun initInfoClickListener() {
        binding.tvSettingsInfo.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.settings_info_link)))
            )
        }
    }

    private fun initOpensourceClickListener() {
        binding.tvSettingsOpensource.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.settings_opensource_link)))
            )
        }
    }

    private fun initFeedbackClickListener() {
        binding.tvSettingsFeedback.setOnClickListener {
            // TODO by 이빵주 : FeedbackActivity 만들어서 띄우기
        }
    }

    private fun initOutRoomClickListener() {
        binding.tvSettingsRoomOut.setOnClickListener {
            startActivity(Intent(this, OutRoomActivity::class.java))
        }
    }

    private fun initWithdrawClickListener() {
        binding.tvSettingsWithdraw.setOnClickListener {
            startActivity(
                Intent(this, WithdrawActivity::class.java)
            )
        }
    }

    private fun initLogoutClickListener() {
        binding.tvSettingsLogout.setOnClickListener { showWarningDialog() }
    }

    private fun initIsSuccessLogoutCollector() {
        repeatOnStarted {
            viewModel.isSuccessLogout.collect { isSuccess ->
                if (isSuccess) {
                    clickDateLogEvent(CLICK_LOG_OUT)
                    ToastMessageUtil.showToast(
                        this@SettingsActivity,
                        getString(R.string.settings_logout_toast)
                    )
                    startActivity(
                        Intent(this, LoginActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        }
                    )
                }
            }
        }
    }

    private fun initIsAllowedLogoutCollector() {
        repeatOnStarted {
            viewModel.isAllowedLogout.collect { isAllowed ->
                if (isAllowed) {
                    viewModel.postLogout()
                }
            }
        }
    }

    private fun showWarningDialog() {
        WarningDialogFragment().apply {
            arguments = Bundle().apply {
                putSerializable(
                    WarningDialogFragment.WARNING_TYPE,
                    WarningType.WARNING_LOGOUT
                )
                putParcelable(
                    WarningDialogFragment.CONFIRM_ACTION,
                    ConfirmClickListener(confirmAction = { viewModel.initIsAllowedLogout(true) })
                )
            }
        }.show(supportFragmentManager, WarningDialogFragment.DIALOG_WARNING)
    }

    companion object {
        const val HAS_ROOM = "HAS_ROOM"
    }
}
