package hous.release.android.util.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import hous.release.designsystem.theme.HousTheme
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.response.ToDoUser

@Composable
fun ToDoUserProfiles(
    selectedUsers: List<ToDoUser>
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = selectedUsers, key = { user -> user.onBoardingId }) { user ->
            ToDoUserProfile(name = user.nickname, homyType = user.homyType)
        }
    }
}

@Composable
fun ToDoUserProfile(
    name: String,
    homyType: HomyType
) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(6.dp))
            .background(
                colorResource(
                    id = R.color.hous_g_1
                )
            )
            .padding(start = 6.dp, end = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(color = colorResource(id = homyType.getHomyColorRes()))
                .size(12.dp)
        ) {
        }
        Spacer(modifier = Modifier.size(3.dp))
        Text(
            text = name,
            color = colorResource(id = R.color.hous_g_6),
            style = HousTheme.typography.b3,
            modifier = Modifier
                .padding(top = 5.dp, bottom = 3.dp)
        )
    }
}

@Preview
@Composable
fun ToDoUserProfilesPreView() {
    ToDoUserProfiles(
        selectedUsers =
        listOf(
            ToDoUser(HomyType.GREEN, nickname = "이준원", onBoardingId = 1),
            ToDoUser(HomyType.RED, nickname = "손연주", onBoardingId = 2),
            ToDoUser(HomyType.YELLOW, nickname = "이영주", onBoardingId = 3),
            ToDoUser(HomyType.BLUE, nickname = "강원용", onBoardingId = 4)
        )
    )
}

@Preview
@Composable
fun ToDoUserProfilePreView() {
    ToDoUserProfile("이준원", HomyType.GREEN)
}
