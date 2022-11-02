package hous.release.android.presentation.settings

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityNotificationSettingsBinding
import hous.release.android.util.binding.BindingActivity

@AndroidEntryPoint
class NotificationSettingsActivity :
    BindingActivity<ActivityNotificationSettingsBinding>(R.layout.activity_notification_settings) {
    private val viewModel by viewModels<NotificationSettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initBackBtnClickListener()
        initNotificationSwitchClickListener()
    }

    private fun initBackBtnClickListener() {
        binding.btnNotiSettingsBack.setOnClickListener { finish() }
    }

    private fun initNotificationSwitchClickListener() {
        binding.switchNotiSettings.setOnClickListener {
            viewModel.patchNotificationSettings(binding.switchNotiSettings.isChecked)
        }
    }
}
