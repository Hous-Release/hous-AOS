package hous.release.android.util.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import hous.release.android.R
import hous.release.domain.entity.Badge
import hous.release.domain.entity.BadgeState

@Composable
fun HousBadge() {
}

@Composable
private fun HousBadgeImage(
    badge: Badge
) {
    Surface(
        modifier = Modifier.size(80.dp),
        elevation = 10.dp,
        shape = CircleShape
    ) {
        when (badge.badgeState) {
            BadgeState.UNLOCK -> {
                AsyncImage(
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
            BadgeState.CHECKED -> CheckedRepresentationBadge()
        }
    }
}

@Composable
private fun CheckedRepresentationBadge() {
    Column(
        modifier = Modifier.background(colorResource(id = R.color.hous_yellow)),
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
                fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_medium)),
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                letterSpacing = (-0.02).sp,
                lineHeight = 3.6.sp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BadgePreview() {
    val badge = Badge(
        badgeId = 1,
        description = "",
        imageUrl = "",
        isAcquired = false,
        isRead = false,
        name = ""
    )
    Column(Modifier.fillMaxSize()) {
        HousBadgeImage(
            badge
        )
    }
//    LazyVerticalGrid(
//        modifier = Modifier.fillMaxSize(),
//        columns = GridCells.Fixed(3),
//        contentPadding = PaddingValues(8.dp),
//        horizontalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        items(3) {
//
//        }
//    }
}
