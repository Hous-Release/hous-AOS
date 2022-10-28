package hous.release.android.presentation.badge

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import hous.release.android.R
import hous.release.android.util.component.HousBadge
import hous.release.domain.entity.Badge

@Composable
fun HousBadgeScreen(
    badgeViewModel: BadgeViewModel,
    backButtonOnClick: () -> Unit
) {
    val uiState by badgeViewModel.uiState.collectAsState()
    val interactionSource = remember { MutableInteractionSource() }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { badgeViewModel.unLockBadges() },
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        item {
            BadgeToolBar(backButtonOnClick)
            RepresentBackground(uiState.representBadge)
            Spacer(modifier = Modifier.height(20.dp))
        }
        gridItems(
            data = uiState.badges,
            columnCount = 3,
            modifier = Modifier.padding(horizontal = 28.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) { index, data ->
            HousBadge(
                badge = data,
                badgeIndex = index,
                selectBadge = badgeViewModel::selectBadge,
                changeRepresentBadge = badgeViewModel::changeRepresentBadge
            )
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
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
            text = stringResource(R.string.badge_title),
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
        DpOffset(68.dp, (-56).dp),
        DpOffset(104.dp, 24.dp)
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.hous_g_7))
            .padding(top = 30.dp, bottom = if (representBadge == null) 56.dp else 21.dp),
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

private fun <T> LazyListScope.gridItems(
    data: List<T>,
    columnCount: Int,
    modifier: Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    itemContent: @Composable BoxScope.(index: Int, T) -> Unit
) {
    val size = data.count()
    val rows = if (size == 0) 0 else 1 + (size - 1) / columnCount
    items(rows, key = { it.hashCode() }) { rowIndex ->
        Row(
            horizontalArrangement = horizontalArrangement,
            modifier = modifier
        ) {
            for (columnIndex in 0 until columnCount) {
                val itemIndex = rowIndex * columnCount + columnIndex
                if (itemIndex < size) {
                    Box(
                        modifier = Modifier.weight(1F, fill = true),
                        propagateMinConstraints = true
                    ) {
                        itemContent(itemIndex, data[itemIndex])
                    }
                } else {
                    Spacer(Modifier.weight(1F, fill = true))
                }
            }
        }
    }
}

@Composable
private fun RepresentBadge(representBadge: Badge?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
        if (representBadge != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = representBadge.name,
                color = colorResource(id = R.color.hous_white),
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(
                            R.font.spoqa_han_sans_neo,
                            FontWeight.W700,
                            FontStyle.Normal
                        )
                    ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    letterSpacing = (-0.02).sp,
                    lineHeight = 5.4.sp
                )
            )
        }
    }
}
