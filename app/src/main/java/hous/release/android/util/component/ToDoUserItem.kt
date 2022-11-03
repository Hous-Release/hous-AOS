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
import hous.release.android.util.extension.getHomyColorRes
import hous.release.android.util.style.HousTheme
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
                .padding(start = 3.dp, top = 15.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                if (todoUser.isChecked) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color = colorResource(id = todoUser.homyType.getHomyColorRes()))
                            .size(10.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.size(11.dp))
            Text(
                text = todoUser.nickname,
                color = colorResource(id = R.color.hous_black),
                style = HousTheme.typography.b2
            )
        }
    }
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
