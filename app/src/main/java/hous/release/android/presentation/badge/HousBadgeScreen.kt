package hous.release.android.presentation.badge

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import hous.release.android.R
import hous.release.android.util.component.HousBadge
import hous.release.domain.entity.Badge
import hous.release.domain.entity.BadgeState

@Composable
fun HousBadgeScreen(
    badges: List<Badge>,
    backButtonOnClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        BadgeToolBar(backButtonOnClick)
        RepresentBackground(null)
        HousBadges(badges)
    }
}

@Composable
private fun BadgeToolBar(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.hous_g_7))
            .padding(start = 15.dp, top = 20.dp)
    ) {
        Image(
            modifier = Modifier
                .clickable { onClick() },
            painter = painterResource(
                id = R.drawable.ic_back
            ),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = "내 배지",
            color = colorResource(id = R.color.hous_white),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_medium)),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                letterSpacing = (-0.02).sp,
                lineHeight = 8.sp
            )
        )
    }
}

@Composable
private fun RepresentBackground(representBadge: Badge?) {
    val starSite = listOf(
        DpOffset((-96).dp, 0.dp),
        DpOffset(64.dp, (-48).dp),
        DpOffset(86.dp, 64.dp)
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.hous_g_7))
            .padding(top = 30.dp, bottom = 56.dp),
        contentAlignment = Alignment.Center
    ) {
        RepresentBadge(representBadge)
        starSite.forEach { site ->
            Image(
                modifier = Modifier
                    .offset(site.x, site.y),
                painter = painterResource(id = R.drawable.ic_badge_star),
                contentDescription = ""
            )
        }
    }
}

@Composable
private fun HousBadges(
    badges: List<Badge>
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(horizontal = 28.dp, vertical = 40.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        items(badges) { badge ->
            HousBadge(badge)
        }
    }
}

@Composable
private fun RepresentBadge(representBadge: Badge?) {
    Surface(
        modifier = Modifier.size(100.dp),
        elevation = 10.dp,
        shape = CircleShape
    ) {
        if (representBadge == null) {
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 16.dp, vertical = 23.dp),
                text = stringResource(id = R.string.represent_badge),
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.hous_g_4),
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(
                            R.font.spoqa_han_sans_neo_medium,
                            FontWeight.W500,
                            FontStyle.Normal
                        )
                    ),
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    letterSpacing = (-0.02).sp,
                    lineHeight = 16.sp
                )
            )
        } else {
            AsyncImage(
                model = representBadge.imageUrl,
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BadgeScreenPreview() {
    val badges = listOf(
        Badge(
            badgeId = 1,
            description = "어서와요\n우리 Hous-에!",
            imageUrl = "",
            isAcquired = false,
            isRead = false,
            name = "두근두근 하우스",
            badgeState = BadgeState.LOCK
        ),
        Badge(
            badgeId = 1,
            description = "어서와요\n우리 Hous-에!",
            imageUrl = "",
            isAcquired = false,
            isRead = false,
            name = "두근두근 하우스",
            badgeState = BadgeState.LOCK
        ),
        Badge(
            badgeId = 1,
            description = "어서와요\n우리 Hous-에!",
            imageUrl = "",
            isAcquired = false,
            isRead = false,
            name = "두근두근 하우스",
            badgeState = BadgeState.LOCK
        ),
        Badge(
            badgeId = 1,
            description = "어서와요\n우리 Hous-에!",
            imageUrl = "",
            isAcquired = false,
            isRead = false,
            name = "두근두근 하우스",
            badgeState = BadgeState.LOCK
        ),
        Badge(
            badgeId = 1,
            description = "어서와요\n우리 Hous-에!",
            imageUrl = "",
            isAcquired = false,
            isRead = false,
            name = "두근두근 하우스",
            badgeState = BadgeState.LOCK
        ),
        Badge(
            badgeId = 1,
            description = "어서와요\n우리 Hous-에!",
            imageUrl = "",
            isAcquired = false,
            isRead = false,
            name = "두근두근 하우스",
            badgeState = BadgeState.LOCK
        ),
        Badge(
            badgeId = 1,
            description = "어서와요\n우리 Hous-에!",
            imageUrl = "",
            isAcquired = false,
            isRead = false,
            name = "두근두근 하우스",
            badgeState = BadgeState.LOCK
        )
    )
    HousBadgeScreen(badges) {}
}
