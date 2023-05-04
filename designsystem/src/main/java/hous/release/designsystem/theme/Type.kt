package hous.release.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hous.release.designsystem.R

private val spoqaHanSansNeo = FontFamily(
    Font(
        R.font.spoqa_han_sans_neo_bold,
        FontWeight.W700,
        FontStyle.Normal
    )
)

private val spoqaHanSansNeoMedium = FontFamily(
    Font(
        R.font.spoqa_han_sans_neo_medium_f,
        FontWeight.W500,
        FontStyle.Normal
    )
)

@Stable
class HousTypography internal constructor(
    h1: TextStyle,
    h2: TextStyle,
    h3: TextStyle,
    h4: TextStyle,
    b1: TextStyle,
    b2: TextStyle,
    b3: TextStyle,
    description: TextStyle
) {
    var h1: TextStyle by mutableStateOf(h1)
        private set
    var h2: TextStyle by mutableStateOf(h2)
        private set
    var h3: TextStyle by mutableStateOf(h3)
        private set
    var h4: TextStyle by mutableStateOf(h4)
        private set
    var b1: TextStyle by mutableStateOf(b1)
        private set
    var b2: TextStyle by mutableStateOf(b2)
        private set
    var b3: TextStyle by mutableStateOf(b3)
        private set
    var description: TextStyle by mutableStateOf(description)
        private set

    fun copy(
        h1: TextStyle = this.h1,
        h2: TextStyle = this.h2,
        h3: TextStyle = this.h3,
        h4: TextStyle = this.h4,
        b1: TextStyle = this.b1,
        b2: TextStyle = this.b2,
        b3: TextStyle = this.b3,
        description: TextStyle = this.description
    ): HousTypography = HousTypography(h1, h2, h3, h4, b1, b2, b3, description)

    fun update(other: HousTypography) {
        h1 = other.h1
        h2 = other.h2
        h3 = other.h3
        h4 = other.h4
        b1 = other.b1
        b2 = other.b2
        b3 = other.b3
        description = other.description
    }
}

@Composable
fun HousTypography(): HousTypography {
    return HousTypography(
        h1 = TextStyle(
            fontFamily = spoqaHanSansNeo,
            fontWeight = FontWeight.Bold,
            fontSize = 28.dp.toSp(),
            letterSpacing = (-0.01).sp,
            lineHeight = 36.4.sp
        ),
        h2 = TextStyle(
            fontFamily = spoqaHanSansNeo,
            fontWeight = FontWeight.Bold,
            fontSize = 22.dp.toSp(),
            letterSpacing = (-0.01).sp,
            lineHeight = 28.6.sp
        ),
        h3 = TextStyle(
            fontFamily = spoqaHanSansNeo,
            fontWeight = FontWeight.Bold,
            fontSize = 20.dp.toSp(),
            letterSpacing = (-0.02).sp,
            lineHeight = 28.sp
        ),
        h4 = TextStyle(
            fontFamily = spoqaHanSansNeo,
            fontWeight = FontWeight.Bold,
            fontSize = 18.dp.toSp(),
            letterSpacing = (-0.02).sp,
            lineHeight = 23.4.sp
        ),
        b1 = TextStyle(
            fontFamily = spoqaHanSansNeoMedium,
            fontWeight = FontWeight.Normal,
            fontSize = 16.dp.toSp(),
            letterSpacing = (-0.02).sp,
            lineHeight = 24.sp
        ),
        b2 = TextStyle(
            fontFamily = spoqaHanSansNeoMedium,
            fontWeight = FontWeight.Normal,
            fontSize = 14.dp.toSp(),
            letterSpacing = (-0.02).sp,
            lineHeight = 21.sp
        ),
        b3 = TextStyle(
            fontFamily = spoqaHanSansNeoMedium,
            fontWeight = FontWeight.Normal,
            fontSize = 13.dp.toSp(),
            letterSpacing = (-0.02).sp,
            lineHeight = 19.5.sp
        ),
        description = TextStyle(
            fontFamily = spoqaHanSansNeoMedium,
            fontWeight = FontWeight.Normal,
            fontSize = 12.dp.toSp(),
            letterSpacing = (-0.02).sp,
            lineHeight = 15.6.sp
        )
    )
}

@Composable
private fun Dp.toSp() = with(LocalDensity.current) { this@toSp.toSp() }
