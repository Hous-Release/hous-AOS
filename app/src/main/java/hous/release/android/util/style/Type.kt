package hous.release.android.util.style

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hous.release.android.R
import hous.release.android.util.component.dpToSp

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
            fontSize = dpToSp(dp = 28.dp),
            letterSpacing = (-0.01).sp,
            lineHeight = 8.4.sp
        ),
        h2 = TextStyle(
            fontFamily = spoqaHanSansNeo,
            fontWeight = FontWeight.Bold,
            fontSize = dpToSp(22.dp),
            letterSpacing = (-0.01).sp,
            lineHeight = 6.6.sp
        ),
        h3 = TextStyle(
            fontFamily = spoqaHanSansNeo,
            fontWeight = FontWeight.Bold,
            fontSize = dpToSp(20.dp),
            letterSpacing = (-0.02).sp,
            lineHeight = 8.sp
        ),
        h4 = TextStyle(
            fontFamily = spoqaHanSansNeo,
            fontWeight = FontWeight.Bold,
            fontSize = dpToSp(18.dp),
            letterSpacing = (-0.02).sp,
            lineHeight = 5.4.sp
        ),
        b1 = TextStyle(
            fontFamily = spoqaHanSansNeoMedium,
            fontWeight = FontWeight.Normal,
            fontSize = dpToSp(16.dp),
            letterSpacing = (-0.02).sp,
            lineHeight = 8.sp
        ),
        b2 = TextStyle(
            fontFamily = spoqaHanSansNeoMedium,
            fontWeight = FontWeight.Normal,
            fontSize = dpToSp(14.dp),
            letterSpacing = (-0.02).sp,
            lineHeight = 7.sp
        ),
        b3 = TextStyle(
            fontFamily = spoqaHanSansNeoMedium,
            fontWeight = FontWeight.Normal,
            fontSize = dpToSp(13.dp),
            letterSpacing = (-0.02).sp,
            lineHeight = 6.5.sp
        ),
        description = TextStyle(
            fontFamily = spoqaHanSansNeoMedium,
            fontWeight = FontWeight.Normal,
            fontSize = dpToSp(12.dp),
            letterSpacing = (-0.02).sp,
            lineHeight = 15.6.sp
        )
    )
}
