package hous.release.android.presentation.todo.member

import androidx.annotation.ColorRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hous.release.android.R
import hous.release.domain.entity.HomyType

@Composable
fun MemberTodoTap(
    currIndex: Int,
    members: List<String>,
    setCurrIndex: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        itemsIndexed(members) { index, item ->
            MemberTapItem(
                memberName = item,
                memberColor = R.color.hous_blue,
                index = index,
                currIndex = currIndex,
                setCurrIndex = setCurrIndex
            )
        }
    }
}

@Composable
private fun MemberTapItem(
    memberName: String,
    @ColorRes memberColor: Int,
    a: HomyType,
    index: Int,
    currIndex: Int,
    setCurrIndex: (Int) -> Unit
) {
//    when (a) {
//        HomyType.RED -> colorResource(id = R.color.hous_red)
//        HomyType.YELLOW -> colorResource(id = R.color.hous_yellow)
//        HomyType.BLUE -> colorResource(id = R.color.hous_blue)
//        HomyType.PURPLE -> colorResource(id = R.color.hous_purple)
//        HomyType.GREEN -> colorResource(id = R.color.hous_green)
//        HomyType.GRAY -> colorResource(id = R.color.hous_g_5)
//    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clickable { setCurrIndex(index) }
                .size(40.dp)
                .clip(CircleShape)
                .background(colorResource(id = memberColor))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            if (index == currIndex) {
                Image(
                    painter = painterResource(id = R.drawable.ic_checked),
                    contentDescription = ""
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = memberName,
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo)),
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                letterSpacing = (-0.3).sp,
                lineHeight = 19.sp
            ),
            color = colorResource(id = if (index == currIndex) R.color.hous_black else R.color.hous_g_5)
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (index == currIndex) {
            Image(
                painter = painterResource(id = R.drawable.ic_daily_tab_indicator),
                contentDescription = ""
            )
        }
    }
}

@Preview
@Composable
fun MemberItemPreview() {
    MemberTapItem(
        memberName = "강원용",
        memberColor = R.color.hous_blue,
        index = 1,
        currIndex = 1,
        setCurrIndex = {}
    )
}

@Preview(showBackground = true)
@Composable
fun MemberTapPreview() {
    var index by remember { mutableStateOf(0) }
    val setCurrIndex: (Int) -> Unit = { i -> index = i }
    MemberTodoTap(
        currIndex = index,
        members = listOf("강원용", "강원용", "강원용"),
        setCurrIndex = { i -> setCurrIndex(i) }
    )
}
