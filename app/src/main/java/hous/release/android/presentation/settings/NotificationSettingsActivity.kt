package hous.release.android.presentation.settings

import android.os.Bundle
import hous.release.android.R
import hous.release.android.databinding.ActivityNotificationSettingsBinding
import hous.release.android.util.binding.BindingActivity

class NotificationSettingsActivity :
    BindingActivity<ActivityNotificationSettingsBinding>(R.layout.activity_notification_settings) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBackBtnClickListener()
    }

    private fun initBackBtnClickListener() {
        binding.btnNotiSettingsBack.setOnClickListener { finish() }
    }
}
