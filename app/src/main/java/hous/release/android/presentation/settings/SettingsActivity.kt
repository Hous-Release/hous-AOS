package hous.release.android.presentation.settings

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import hous.release.android.BuildConfig
import hous.release.android.R
import hous.release.android.databinding.ActivitySettingsBinding
import hous.release.android.presentation.out_room.OutRoomActivity
import hous.release.android.presentation.withdraw.WithdrawActivity
import hous.release.android.util.binding.BindingActivity

class SettingsActivity : BindingActivity<ActivitySettingsBinding>(R.layout.activity_settings) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSettingsMode()
        initBackClickListener()
        initNotiSettingClickListener()
        initInfoClickListener()
        initFeedbackClickListener()
        initOutRoomClickListener()
        initWithdrawClickListener()
    }

    private fun initSettingsMode() {
        binding.hasRoom = intent.getBooleanExtra(HAS_ROOM, true)
    }

    private fun initBackClickListener() {
        binding.btnSettingsBack.setOnClickListener { finish() }
    }

    private fun initNotiSettingClickListener() {
        binding.btnSettingsNotification.setOnClickListener {
            startActivity(Intent(this, NotificationSettingsActivity::class.java))
        }
    }

    private fun initInfoClickListener() {
        binding.btnSettingsInfo.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.settings_info_link)))
            )
        }
    }

    private fun initFeedbackClickListener() {
        binding.btnSettingsFeedback.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_SEND).apply {
                    type = "plain/text"
                    putExtra(
                        Intent.EXTRA_EMAIL,
                        arrayOf(getString(R.string.settings_feedback_email))
                    )
                    putExtra(Intent.EXTRA_SUBJECT, getString(R.string.settings_feedback_subject))
                    putExtra(
                        Intent.EXTRA_TEXT,
                        getString(
                            R.string.settings_feedback_content,
                            getDeviceName(),
                            Build.VERSION.SDK_INT.toString(),
                            BuildConfig.VERSION_NAME
                        )
                    )
                }
            )
        }
    }

    private fun initOutRoomClickListener() {
        binding.tvSettingsRoomOut.setOnClickListener {
            startActivity(Intent(this, OutRoomActivity::class.java))
        }
    }

    private fun initWithdrawClickListener() {
        binding.tvSettingsWithdraw.setOnClickListener {
            startActivity(Intent(this, WithdrawActivity::class.java))
        }
    }

    private fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            capitalize(model)
        } else {
            "${capitalize(manufacturer)} $model"
        }
    }

    private fun capitalize(str: String?): String {
        return if (str.isNullOrEmpty()) {
            getString(R.string.settings_feedback_empty_string)
        } else {
            val first = str[0]
            Character.toUpperCase(first).toString() + str.substring(1)
        }
    }

    companion object {
        const val HAS_ROOM = "HAS_ROOM"
    }
}
