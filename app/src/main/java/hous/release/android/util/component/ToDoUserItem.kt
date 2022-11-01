package hous.release.android.util.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.android.R
import hous.release.android.util.theme.b2
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.response.ToDoUser

@Composable
fun ToDoUserItem(
    userIdx: Int,
    todoUser: ToDoUser,
    checkUser: (userIdx: Int) -> Unit = {},
    hideKeyBoard: () -> Unit = {}
) {
    Column(modifier = Modifier.wrapContentSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    hideKeyBoard()
                    checkUser(userIdx)
                }
                .padding(start = 3.dp)
                .padding(top = 15.dp)
                .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (todoUser.isChecked) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = colorResource(id = R.color.hous_white))
                        .size(14.dp)
                        .border(
                            BorderStroke(
                                1.dp,
                                colorResource(id = todoUser.homyType.getHomyColorRes())
                            ),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color = colorResource(id = todoUser.homyType.getHomyColorRes()))
                            .size(10.dp)
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = colorResource(id = R.color.hous_white))
                        .size(14.dp)
                        .border(
                            BorderStroke(
                                1.dp,
                                colorResource(id = todoUser.homyType.getHomyColorRes())
                            ),
                            CircleShape
                        )

                ) {
                }
            }
            Spacer(modifier = Modifier.size(11.dp))
            Text(
                text = todoUser.nickname,
                color = colorResource(id = R.color.hous_black),
                style = b2.copy(fontSize = dpToSp(dp = 14.dp))
            )
        }
    }
}

fun HomyType.getHomyColorRes() = when (this) {
    HomyType.RED -> R.color.hous_red
    HomyType.YELLOW -> R.color.hous_yellow
    HomyType.BLUE -> R.color.hous_blue
    HomyType.PURPLE -> R.color.hous_purple
    HomyType.GREEN -> R.color.hous_green
    HomyType.GRAY -> R.color.hous_g_5 // TODO 나중에 g몇인지 찾고 바꾸자
}

@Preview
@Composable
fun ToDoUserItemPreview() {
    Surface(color = colorResource(id = R.color.white)) {
        ToDoUserItem(
            userIdx = 0,
            todoUser = ToDoUser(
                nickname = "이준원",
                homyType = HomyType.GREEN,
                isChecked = true
            )
        )
    }
}
