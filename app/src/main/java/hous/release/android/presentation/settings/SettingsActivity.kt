package hous.release.android.presentation.settings

import android.content.Intent
import android.os.Bundle
import hous.release.android.R
import hous.release.android.databinding.ActivitySettingsBinding
import hous.release.android.util.binding.BindingActivity

class SettingsActivity : BindingActivity<ActivitySettingsBinding>(R.layout.activity_settings) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBackBtnClickListener()
        initNotiSettingClickListener()
    }

    private fun initBackBtnClickListener() {
        binding.btnSettingsBack.setOnClickListener { finish() }
    }

    private fun initNotiSettingClickListener() {
        binding.btnSettingsNotification.setOnClickListener {
            startActivity(Intent(this, NotificationSettingsActivity::class.java))
        }
    }
}
