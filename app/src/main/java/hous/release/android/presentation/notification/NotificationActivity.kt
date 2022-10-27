package hous.release.android.presentation.notification

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityNotificationBinding
import hous.release.android.util.binding.BindingActivity

@AndroidEntryPoint
class NotificationActivity :
    BindingActivity<ActivityNotificationBinding>(R.layout.activity_notification) {
    private val viewModel by viewModels<NotificationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getNotification()
    }
}
