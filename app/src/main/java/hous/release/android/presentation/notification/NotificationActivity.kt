package hous.release.android.presentation.notification

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hous.release.android.R
import hous.release.android.databinding.ActivityNotificationBinding
import hous.release.android.presentation.notification.paging.NotificationPagingAdapter
import hous.release.android.util.binding.BindingActivity
import hous.release.android.util.extension.repeatOnStarted
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NotificationActivity :
    BindingActivity<ActivityNotificationBinding>(R.layout.activity_notification) {
    private val viewModel by viewModels<NotificationViewModel>()
    private val notificationAdapter = NotificationPagingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBackBtnClickListener()
        initNotificationAdapter()
        collectNotificationList()
    }

    private fun initBackBtnClickListener() {
        binding.btnNotificationBack.setOnClickListener {
            finish()
        }
    }

    private fun initNotificationAdapter() {
        binding.rvNotification.adapter = notificationAdapter
    }

    private fun collectNotificationList() {
        repeatOnStarted {
            viewModel.getNotification().collectLatest { notification ->
                notificationAdapter.submitData(notification)
            }
        }
    }
}
