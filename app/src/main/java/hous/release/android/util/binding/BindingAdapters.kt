package hous.release.android.util.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import hous.release.android.R
import hous.release.domain.entity.NotificationType

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("notificationIcon")
    fun ImageView.setNotificationIcon(type: String) {
        setImageResource(
            when (NotificationType.valueOf(type)) {
                NotificationType.TODO -> R.drawable.ic_notification_to_do
                NotificationType.RULE -> R.drawable.ic_notification_rules
                NotificationType.BADGE -> R.drawable.ic_notification_badge
            }
        )
    }

    @JvmStatic
    @BindingAdapter("app:pushNotificationState")
    fun ImageView.setPushAlarmState(isSelected: Boolean?) {
        if (isSelected == null) return
        this.isSelected = isSelected
    }
}
