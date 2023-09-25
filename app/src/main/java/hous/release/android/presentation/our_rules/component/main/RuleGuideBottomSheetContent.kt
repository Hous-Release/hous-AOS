package hous.release.android.presentation.our_rules.component.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextAlign.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.designsystem.theme.HousBlack
import hous.release.designsystem.theme.HousBlue
import hous.release.designsystem.theme.HousG2
import hous.release.designsystem.theme.HousG6
import hous.release.designsystem.theme.HousTheme
import hous.release.designsystem.theme.HousWhite

// https://github.com/android/snippets/blob/5ae1f7852164d98d055b3cc6b463705989cff231/compose/snippets/src/main/java/com/example/compose/snippets/layouts/PagerSnippets.kt#L93-L103

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RuleGuideBottomSheetContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, bottom = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val pageCount = 3
        val pagerState = rememberPagerState(pageCount = { pageCount })
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            beyondBoundsPageCount = 2, // to place more pages before and after the visible pages
            state = pagerState
        ) { page ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "룰스 고정하기",
                    color = HousBlack,
                    textAlign = TextAlign.Center,
                    style = HousTheme.typography.b1
                )
                Spacer(Modifier.height(13.dp))

                Text(
                    text = "Page: $page",
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(20.dp))

                Text(
                    text = "룰스 고정 어쩌구 저쩌구",
                    color = HousG6,
                    textAlign = TextAlign.Center,
                    style = HousTheme.typography.description
                )
            }
        }
        Spacer(Modifier.height(17.dp))
        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pageCount) { idx ->
                PageIndicator(pagerState.currentPage == idx)
                if (idx != pageCount - 1) {
                    Spacer(Modifier.width(14.dp))
                }
            }
        }
    }
}

@Composable
private fun PageIndicator(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(8.dp)
            .clip(CircleShape)
            .background(
                color = if (isSelected) HousBlue else HousG2
            )
    )
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    HousTheme {
        Surface(color = HousWhite) {
            RuleGuideBottomSheetContent()
        }
    }
}