package hous.release.android.util.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import hous.release.android.R
import hous.release.domain.entity.Badge
import hous.release.domain.entity.BadgeState

@Composable
fun HousBadge(
    badge: Badge,
    badgeIndex: Int,
    selectBadge: (Int) -> Unit,
    changeRepresentBadge: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HousBadgeImage(
            badge = badge,
            badgeIndex = badgeIndex,
            selectBadge = selectBadge,
            changeRepresentBadge = changeRepresentBadge
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = badge.name,
            color = colorResource(id = R.color.hous_g_7),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_medium)),
                fontWeight = FontWeight.Normal,
                fontSize = dpToSp(13.dp),
                letterSpacing = (-0.02).sp,
                lineHeight = 19.5.sp
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = badge.description,
            color = colorResource(id = R.color.hous_g_3),
            style = TextStyle(
                fontFamily = FontFamily(
                    Font(
                        R.font.spoqa_han_sans_neo_medium,
                        FontWeight.W500,
                        FontStyle.Normal
                    )
                ),
                fontWeight = FontWeight.Normal,
                fontSize = dpToSp(12.dp),
                letterSpacing = (-0.02).sp,
                lineHeight = 16.sp
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun HousBadgeImage(
    badge: Badge,
    badgeIndex: Int,
    selectBadge: (Int) -> Unit,
    changeRepresentBadge: (Int) -> Unit
) {
    Surface(
        modifier = Modifier.size(80.dp),
        elevation = 10.dp,
        shape = CircleShape
    ) {
        when (badge.badgeState) {
            BadgeState.UNLOCK -> {
                AsyncImage(
                    modifier = Modifier.clickable { selectBadge(badgeIndex) },
                    model = badge.imageUrl,
                    contentDescription = null
                )
            }
            BadgeState.LOCK -> {
                Image(
                    modifier = Modifier
                        .background(colorResource(id = R.color.hous_g_8))
                        .padding(28.dp),
                    painter = painterResource(id = R.drawable.ic_badge_lock),
                    contentDescription = ""
                )
            }
            BadgeState.CHECKED -> CheckedRepresentationBadge(
                badgeId = badge.badgeId,
                changeRepresentBadge = changeRepresentBadge
            )
            BadgeState.REPRESENT -> {
                AsyncImage(
                    model = badge.imageUrl,
                    contentDescription = null
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(id = R.color.hous_g_7).copy(alpha = 0.8f)),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        modifier = Modifier.fillMaxSize().padding(vertical = 24.dp),
                        text = "대표배지로\n설정됨",
                        color = colorResource(id = R.color.hous_white),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontFamily = FontFamily(
                                Font(
                                    R.font.spoqa_han_sans_neo_medium,
                                    FontWeight.W500,
                                    FontStyle.Normal
                                )
                            ),
                            fontWeight = FontWeight.Normal,
                            fontSize = dpToSp(12.dp),
                            letterSpacing = (-0.02).sp,
                            lineHeight = 16.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun CheckedRepresentationBadge(
    badgeId: Int,
    changeRepresentBadge: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { changeRepresentBadge(badgeId) }
            .background(colorResource(id = R.color.hous_yellow)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_badge_check),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = stringResource(R.string.badge_checked),
            color = colorResource(id = R.color.hous_white),
            style = TextStyle(
                fontFamily = FontFamily(
                    Font(
                        R.font.spoqa_han_sans_neo_medium,
                        FontWeight.W500,
                        FontStyle.Normal
                    )
                ),
                fontWeight = FontWeight.Normal,
                fontSize = dpToSp(12.dp),
                letterSpacing = (-0.02).sp,
                lineHeight = 16.sp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BadgePreview() {
    val badge = Badge(
        badgeId = 1,
        description = "어서와요\n우리 Hous-에!",
        imageUrl = "",
        isAcquired = false,
        isRead = false,
        name = "두근두근 하우스",
        badgeState = BadgeState.UNLOCK
    )
    Column(Modifier.fillMaxSize()) {
        HousBadge(badge, 0, {}) {}
    }
}
