package hous.release.android.presentation.todo.member

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hous.release.android.R
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.response.MemberTodoContent

@Composable
fun MemberTodoTap(
    currIndex: Int,
    members: List<MemberTodoContent>,
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
                member = item,
                index = index,
                currIndex = currIndex,
                setCurrIndex = setCurrIndex
            )
        }
    }
}

@Composable
private fun MemberTapItem(
    member: MemberTodoContent,
    index: Int,
    currIndex: Int,
    setCurrIndex: (Int) -> Unit
) {
    val memberColor = when (member.color) {
        HomyType.RED -> colorResource(id = R.color.hous_red)
        HomyType.YELLOW -> colorResource(id = R.color.hous_yellow)
        HomyType.BLUE -> colorResource(id = R.color.hous_blue)
        HomyType.PURPLE -> colorResource(id = R.color.hous_purple)
        HomyType.GREEN -> colorResource(id = R.color.hous_green)
        HomyType.GRAY -> colorResource(id = R.color.hous_g_5)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clickable { setCurrIndex(index) }
                .size(40.dp)
                .clip(CircleShape)
                .background(memberColor)
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
            text = member.userName,
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
