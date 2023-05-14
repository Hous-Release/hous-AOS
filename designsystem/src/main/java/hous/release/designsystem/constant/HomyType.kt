package hous.release.designsystem.constant

import androidx.compose.ui.graphics.Color
import hous.release.designsystem.theme.HousBlue
import hous.release.designsystem.theme.HousG3
import hous.release.designsystem.theme.HousGreen
import hous.release.designsystem.theme.HousPurple
import hous.release.designsystem.theme.HousRed
import hous.release.designsystem.theme.HousYellow

@JvmInline
value class HomyType(val type: Int) {
    init {
        require(type < 6) { ERROR_MSG }
    }

    fun getColor(): Color =
        when (type) {
            YELLOW -> HousYellow
            RED -> HousRed
            BLUE -> HousBlue
            PURPLE -> HousPurple
            GREEN -> HousGreen
            else -> HousG3
        }

    companion object {
        const val YELLOW = 0
        const val RED = 1
        const val BLUE = 2
        const val PURPLE = 3
        const val GREEN = 4
        const val ERROR_MSG = "유요하지 않은 homy type 값입니다."
    }
}
