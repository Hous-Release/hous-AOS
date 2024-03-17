package hous.release.android.presentation.our_rules.component.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch

// https://github.com/android/snippets/blob/5ae1f7852164d98d055b3cc6b463705989cff231/compose/snippets/src/main/java/com/example/compose/snippets/layouts/PagerSnippets.kt#L93-L103

private val titleList = listOf(
    "우리집 Rules란?",
    "대표 Rules 선택하기",
    "대표 Rules 선택 시 주의!"
)

private val descriptionList = listOf(
    "Rules는 우리 호미들이 꼭 지켜야 하는 규칙이에요!",
    "우리 집 대표 Rules를 선택하면 홈에서 바로 확인할 수 있어요!",
    "대표 Rules는 딱 3개까지만 고정할 수 있어요!"
)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RuleGuideBottomSheetContent() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 20.dp, bottom = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val pageCount = 3
        val pagerState = rememberPagerState(pageCount = { pageCount })
        val coroutineScope = rememberCoroutineScope()
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            beyondBoundsPageCount = 2, // to place more pages before and after the visible pages
            state = pagerState
        ) { page ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = titleList[page],
                    color = HousBlack,
                    textAlign = TextAlign.Center,
                    style = HousTheme.typography.b1
                )
                Spacer(Modifier.height(13.dp))
                RuleGuideLottie(idx = page)
                Spacer(Modifier.height(20.dp))

                Text(
                    text = descriptionList[page],
                    color = HousG6,
                    textAlign = TextAlign.Center,
                    style = HousTheme.typography.description
                )
            }
        }
        Spacer(Modifier.height(17.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            PageIndicators(pageCount, pagerState.currentPage, onSelected = { targetPage ->
                coroutineScope.launch {
                    pagerState.animateScrollToPage(targetPage)
                }
            })
        }
    }
}

@Composable
private fun PageIndicators(pageCount: Int, currentPage: Int, onSelected: (Int) -> Unit) {
    repeat(pageCount) { idx ->
        PageIndicator(currentPage == idx, onSelected = { onSelected(idx) })
        if (idx != pageCount - 1) {
            Spacer(Modifier.width(14.dp))
        }
    }
}

@Composable
private fun PageIndicator(isSelected: Boolean, onSelected: () -> Unit) {
    Box(
        modifier = Modifier.padding(8.dp).size(8.dp).clip(CircleShape).background(
            color = if (isSelected) HousBlue else HousG2
        ).clickable(
            onClick = onSelected
        )
    )
}

@Composable
@Preview(showBackground = true)
private fun RuleGuideBottomSheetContentPreview() {
    HousTheme {
        RuleGuideBottomSheetContent()
    }
}
