package hous.release.android.util.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import hous.release.android.R
import hous.release.domain.entity.HomyType
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
    @BindingAdapter("setImage")
    fun ImageView.setImage(imgUrl: String?) {
        this.let {
            Glide.with(context)
                .load(imgUrl)
                .into(this)
        }
    }

    @JvmStatic
    @BindingAdapter("personalityResultColor")
    fun TextView.setPersonalityResultColor(color: String) {
        setTextColor(
            ContextCompat.getColor(
                context,
                when (HomyType.valueOf(color)) {
                    HomyType.RED -> R.color.hous_red
                    HomyType.YELLOW -> R.color.hous_yellow
                    HomyType.GREEN -> R.color.hous_green
                    HomyType.BLUE -> R.color.hous_blue
                    HomyType.PURPLE -> R.color.hous_purple
                    else -> -1
                }
            )
        )
    }

    @JvmStatic
    @BindingAdapter("personalityResultIcon")
    fun ImageView.setPersonalityResultIcon(color: String) {
        setImageResource(
            when (HomyType.valueOf(color)) {
                HomyType.RED -> R.drawable.ic_personality_result_check_red
                HomyType.YELLOW -> R.drawable.ic_personality_result_check_yellow
                HomyType.GREEN -> R.drawable.ic_personality_result_check_green
                HomyType.BLUE -> R.drawable.ic_personality_result_check_blue
                HomyType.PURPLE -> R.drawable.ic_personality_result_check_purple
                else -> -1
            }
        )
    }
}
