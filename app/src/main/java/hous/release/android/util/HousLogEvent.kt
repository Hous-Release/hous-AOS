package hous.release.android.util

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.BuildConfig
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object HousLogEvent {
    private const val DATE_FORMAT = "yyyy-MM-dd"
    private val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)

    const val SCREEN_HOME = "SCREEN_HOME"
    const val SCREEN_RULES = "SCREEN_RULES"
    const val SCREEN_TODO_DETAIL = "SCREEN_TODO_DETAIL"
    const val SCREEN_BADGE = "SCREEN_BADGE"
    const val SCREEN_ALARM = "SCREEN_ALARM"

    const val CLICK_RULES_SETTING = "CLICK_RULES_SETTING"
    const val CLICK_HOME_HOMIES = "CLICK_HOME_HOMIES"
    const val CLICK_COPY = "CLICK_COPY"
    const val CLICK_BLANK = "CLICK_BLANK"
    const val CLICK_ADD_TODO = "CLICK_ADD_TODO"
    const val CLICK_RE_TEST = "CLICK_RE_TEST"
    const val CLICK_MY_PERSONALITY = "CLICK_MY_PERSONALITY"
    const val CLICK_ROOM_OUT = "CLICK_ROOM_OUT"
    const val CLICK_WITHDRAW = "CLICK_SIGN_OUT"
    const val CLICK_DROP_OUT_TEST = "CLICK_DROP_OUT_TEST"
    const val CLICK_FINISH_TEST = "CLICK_FINISH_TEST"

    fun enterScreenLogEvent(screenClass: String, screenName: String) {
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass)
            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        }
    }

    fun clickLogEvent(event: String) {
        if (!BuildConfig.DEBUG) Firebase.analytics.logEvent(event, null)
    }

    fun clickDateLogEvent(event: String) {
        if (!BuildConfig.DEBUG) {
            val date = LocalDateTime.now()
            val formattedDate = date.format(formatter)
            Firebase.analytics.logEvent(event) {
                param(FirebaseAnalytics.Param.START_DATE, formattedDate)
            }
        }
    }

    fun openAppEvent() {
        if (!BuildConfig.DEBUG) {
            Firebase.analytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null)
        }
    }
}
