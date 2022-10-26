package hous.release.android.util

import hous.release.android.R
import hous.release.domain.entity.ProfileSet

fun getProfileSet(colorType: String): ProfileSet {
    return when (colorType) {
        "RED" -> ProfileSet(
            colorBg = R.color.hous_red_profile,
            colorText = R.color.hous_red,
            personality = R.string.personality_red
        )
        "YELLOW" -> ProfileSet(
            colorBg = R.color.hous_yellow,
            colorText = R.color.hous_yellow,
            personality = R.string.personality_yellow
        )
        "GREEN" -> ProfileSet(
            colorBg = R.color.hous_green,
            colorText = R.color.hous_green,
            personality = R.string.personality_green
        )
        "BLUE" -> ProfileSet(
            colorBg = R.color.hous_blue,
            colorText = R.color.hous_blue,
            personality = R.string.personality_blue
        )
        else -> ProfileSet(
            colorBg = R.color.hous_purple,
            colorText = R.color.hous_purple,
            personality = R.string.personality_purple
        )
    }
}
