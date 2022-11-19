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
import java.util.Calendar
import java.util.Date

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
                        HomyType.GRAY -> R.color.hous_blue_l1
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
                    HomyType.GRAY -> R.string.personality_purple
                }
            )
        }
    }

    @JvmStatic
    @BindingAdapter("lottieColor", "badge")
    fun LottieAnimationView.lottieFileName(color: String?, badge: String?) {
        color?.let {
            when (HomyType.valueOf(color)) {
                HomyType.RED -> {
                    if (badge.isNullOrEmpty()) {
                        setAnimation("profile_red_no.json")
                    } else {
                        setAnimation("profile_red_yes.json")
                    }
                }
                HomyType.YELLOW -> {
                    if (badge.isNullOrEmpty()) {
                        setAnimation("profile_yellow_no.json")
                    } else {
                        setAnimation("profile_yellow_yes.json")
                    }
                }
                HomyType.GREEN -> {
                    if (badge.isNullOrEmpty()) {
                        setAnimation("profile_green_no.json")
                    } else {
                        setAnimation("profile_green_yes.json")
                    }
                }
                HomyType.BLUE -> {
                    if (badge.isNullOrEmpty()) {
                        setAnimation("profile_blue_no.json")
                    } else {
                        setAnimation("profile_blue_yes.json")
                    }
                }
                HomyType.PURPLE -> {
                    if (badge.isNullOrEmpty()) {
                        setAnimation("profile_purple_no.json")
                    } else {
                        setAnimation("profile_purple_yes.json")
                    }
                }
                HomyType.GRAY -> {
                    setAnimation("profile_gray.json")
                }
            }
            playAnimation()
        }
    }

    @JvmStatic
    @BindingAdapter("lottieBackground")
    fun LottieAnimationView.background(color: String?) {
        color?.let {
            setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    when (HomyType.valueOf(color)) {
                        HomyType.RED -> R.color.hous_red_b_1
                        HomyType.YELLOW -> R.color.hous_yellow_b_1
                        HomyType.GREEN -> R.color.hous_green_b_1
                        HomyType.BLUE -> R.color.hous_blue_l1
                        HomyType.PURPLE -> R.color.hous_purple_b_1
                        HomyType.GRAY -> R.color.hous_blue_l2
                    }
                )
            )
        }
    }

    @JvmStatic
    @BindingAdapter("isLottieForDayOfWeek")
    fun LottieAnimationView.setLottieForDayOfWeek(isLottieForDayOfWeek: Boolean) {
        clipToOutline = true
        if (isLottieForDayOfWeek) {
            val calendar: Calendar = Calendar.getInstance()
            calendar.time = Date()
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            setAnimation(
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

    @JvmStatic
    @BindingAdapter("homyType")
    fun ImageView.setHomyType(homyType: HomyType) {
        setImageResource(
            when (homyType) {
                HomyType.RED -> R.drawable.ic_profile_red
                HomyType.YELLOW -> R.drawable.ic_profile_yellow
                HomyType.BLUE -> R.drawable.ic_profile_gray
                HomyType.PURPLE -> R.drawable.ic_profile_purple
                HomyType.GREEN -> R.drawable.ic_profile_green
                HomyType.GRAY -> R.drawable.ic_profile_gray
            }
        )
    }
}
