package hous.release.android.util

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

object HousLogEvent {
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

    fun clickLogEvent(event: String) {
        Firebase.analytics.logEvent(event, null)
    }
}
