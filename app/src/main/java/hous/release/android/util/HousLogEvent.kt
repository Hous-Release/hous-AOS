package hous.release.android.util

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.BuildConfig
import com.google.firebase.ktx.Firebase
import hous.release.domain.entity.SplashState
import hous.release.domain.usecase.GetSplashStateUseCase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

object HousLogEvent {
    @Inject
    lateinit var getSplashStateUseCase: GetSplashStateUseCase
    private const val DATE_FORMAT = "yyyy-MM-dd"
    private val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)

    const val FIRST_OPEN = "FIRST_OPEN"
    const val SCREEN_HOME = "SCREEN_HOME"
    const val SCREEN_FIRST_HOME = "SCREEN_FIRST_HOME"

    const val CLICK_RULES = "CLICK_RULES"
    const val CLICK_ADD_RULES = "CLICK_ADD_RULES"
    const val CLICK_HOME_HOMIES = "CLICK_HOME_HOMIES"
    const val CLICK_COPY = "CLICK_COPY"
    const val CLICK_BLANK = "CLICK_BLANK"

    const val CLICK_TODO_DETAIL = "CLICK_TODO_DETAIL"
    const val CLICK_ADD_TODO = "CLICK_ADD_TODO"

    const val CLICK_BADGE = "CLICK_BADGE"
    const val CLICK_ALARM = "CLICK_ALARM"
    const val CLICK_AGAIN_TEST = "CLICK_AGAIN_TEST"
    const val CLICK_MY_PERSONALITY = "CLICK_MY_PERSONALITY"
    const val CLICK_ROOM_OUT = "CLICK_ROOM_OUT"
    const val CLICK_SIGN_OUT = "CLICK_SIGN_OUT"
    const val DROP_OUT_TEST = "DROP_OUT_TEST"

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
        if (!BuildConfig.DEBUG && getSplashStateUseCase() == SplashState.TUTORIAL) {
            Firebase.analytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null)
        }
    }
}
