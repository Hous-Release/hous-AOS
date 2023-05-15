package hous.release.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.designsystem.R
import hous.release.designsystem.theme.HousTheme

@Composable
fun HousToolbarSlot(
    modifier: Modifier,
    leadingIcon: @Composable () -> Unit = {},
    title: String = "",
    trailingIcon: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            leadingIcon()
            Text(
                text = title,
                style = HousTheme.typography.b1
            )
        }
        trailingIcon()
    }
}

@Preview(name = "앞쪽 아이콘, title, 뒤쪽 아이콘 모두 있는 Tool Bar")
@Composable
private fun HousToolBarPreview() {
    HousTheme {
        HousToolbarSlot(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 24.dp, top = 20.dp, bottom = 20.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = null
                )
            },
            title = "설정",
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_move),
                    contentDescription = null
                )
            }
        )
    }
}

@Preview(name = "앞쪽 아이콘, title 은 있지만, 뒤쪽 아이콘은 없는 Tool Bar")
@Composable
private fun HousToolBarPreview2() {
    HousTheme {
        HousToolbarSlot(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 20.dp, bottom = 20.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = null
                )
            },
            title = "설정"
        )
    }
}

@Preview(name = "앞쪽 아이콘, 뒤쪽 아이콘은 있지만, title 은 없는 Tool Bar")
@Composable
private fun HousToolBarPreview3() {
    HousTheme {
        HousToolbarSlot(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 20.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = null
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_move),
                    contentDescription = null
                )
            }
        )
    }
}

@Preview(name = "title, 뒤쪽 아이콘은 있지만, 앞쪽 아이콘은 없는 Tool Bar")
@Composable
private fun HousToolBarPreview4() {
    HousTheme {
        HousToolbarSlot(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 24.dp, top = 20.dp, bottom = 20.dp),
            title = "설정",
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_move),
                    contentDescription = null
                )
            }
        )
    }
}
