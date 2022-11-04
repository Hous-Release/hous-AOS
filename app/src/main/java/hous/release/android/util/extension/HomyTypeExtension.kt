package hous.release.android.util.extension

import hous.release.android.R
import hous.release.domain.entity.HomyType

fun HomyType.getHomyColorRes() = when (this) {
    HomyType.RED -> R.color.hous_red
    HomyType.YELLOW -> R.color.hous_yellow
    HomyType.BLUE -> R.color.hous_blue
    HomyType.PURPLE -> R.color.hous_purple
    HomyType.GREEN -> R.color.hous_green
    HomyType.GRAY -> R.color.hous_g_3
}
