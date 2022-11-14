package hous.release.android.util.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import hous.release.android.R
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.NotificationType
import java.util.*

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
    fun TextView.setPersonalityResultColor(color: String?) {
        color?.let {
            setTextColor(
                ContextCompat.getColor(
                    context,
                    when (HomyType.valueOf(color)) {
                        HomyType.RED -> R.color.hous_red
                        HomyType.YELLOW -> R.color.hous_yellow
                        HomyType.GREEN -> R.color.hous_green
                        HomyType.BLUE -> R.color.hous_blue
                        HomyType.PURPLE -> R.color.hous_purple
                        HomyType.GRAY -> -1
                    }
                )
            )
        }
    }

    @JvmStatic
    @BindingAdapter("personalityResultIcon")
    fun ImageView.setPersonalityResultIcon(color: String?) {
        color?.let {
            setImageResource(
                when (HomyType.valueOf(color)) {
                    HomyType.RED -> R.drawable.ic_personality_result_check_red
                    HomyType.YELLOW -> R.drawable.ic_personality_result_check_yellow
                    HomyType.GREEN -> R.drawable.ic_personality_result_check_green
                    HomyType.BLUE -> R.drawable.ic_personality_result_check_blue
                    HomyType.PURPLE -> R.drawable.ic_personality_result_check_purple
                    HomyType.GRAY -> -1
                }
            )
        }
    }

    @JvmStatic
    @BindingAdapter("app:notificationState")
    fun ImageView.setNotificationState(isSelected: Boolean?) {
        if (isSelected == null) return
        this.isSelected = isSelected
    }

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun TextView.visibility(text: String?) {
        visibility = if (text.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("homieProfileBackground")
    fun ConstraintLayout.background(color: String?) {
        color?.let {
            setBackgroundResource(
                when (HomyType.valueOf(color)) {
                    HomyType.RED -> R.drawable.ic_homie_profile_red
                    HomyType.YELLOW -> R.drawable.ic_homie_profile_yellow
                    HomyType.GREEN -> R.drawable.ic_homie_profile_green
                    HomyType.BLUE -> R.drawable.ic_homie_profile_blue
                    HomyType.PURPLE -> R.drawable.ic_homie_profile_purple
                    HomyType.GRAY -> -1
                }
            )
        }
    }

    @JvmStatic
    @BindingAdapter("personalityTitle")
    fun TextView.text(color: String?) {
        color?.let {
            setText(
                when (HomyType.valueOf(color)) {
                    HomyType.RED -> R.string.personality_red
                    HomyType.YELLOW -> R.string.personality_yellow
                    HomyType.GREEN -> R.string.personality_green
                    HomyType.BLUE -> R.string.personality_blue
                    HomyType.PURPLE -> R.string.personality_purple
                    HomyType.GRAY -> -1
                }
            )
        }
    }

    @JvmStatic
    @BindingAdapter("isLottieForDayOfWeek")
    fun LottieAnimationView.setLottieForDayOfWeek(isLottieForDayOfWeek: Boolean) {
        this.clipToOutline = true
        if (isLottieForDayOfWeek) {
            val calendar: Calendar = Calendar.getInstance()
            calendar.time = Date()
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            this.setAnimation(
                // TODO : 토요일, 일요일 로티 나오면 수정해야함
                when (dayOfWeek) {
                    Calendar.MONDAY -> "welcome_yellow_monday.json"
                    Calendar.TUESDAY -> "welcome_red_tuesday.json"
                    Calendar.WEDNESDAY -> "welcome_blue_wednesday.json"
                    Calendar.THURSDAY -> "welcome_purple_thursday.json"
                    Calendar.FRIDAY -> "welcome_green_friday.json"
                    Calendar.SATURDAY -> "welcome_yellow_monday.json"
                    Calendar.SUNDAY -> "welcome_yellow_monday.json"
                    else -> "welcome_yellow_monday.json"
                }
            )
        }
    }
}
