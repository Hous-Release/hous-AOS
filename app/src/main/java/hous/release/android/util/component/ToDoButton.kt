package hous.release.android.util.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hous.release.android.R
import hous.release.android.presentation.our_rules.type.ButtonState
import hous.release.android.util.style.HousTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun ToDoButton(
    buttonState: ButtonState,
    name: String,
    finish: () -> Boolean,
    putToDo: () -> Job,
    coroutineScope: CoroutineScope
) {
    val buttonBackground =
        if (buttonState == ButtonState.ACTIVE) colorResource(id = R.color.hous_blue) else colorResource(
            id = R.color.hous_g_4
        )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
            .background(buttonBackground)
            .clickable(buttonState == ButtonState.ACTIVE) {
                coroutineScope.launch {
                    putToDo().join()
                    finish()
                }
            },
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = name,
            style = HousTheme.typography.b1,
            color = colorResource(id = R.color.hous_white),
            modifier = Modifier
                .padding(top = 10.dp)
                .padding(bottom = 9.dp)
        )
    }
}

@Preview
@Composable
fun ToDoButtonPreview() {
    val buttonBackground = colorResource(id = R.color.hous_g_4)
    val sampleData = mutableListOf<String>()
    (1..50).forEach {
        sampleData.add("Sample Data $it")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            sampleData.forEach {
                Text(
                    text = it,
                    color = colorResource(id = R.color.black),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }
            Spacer(modifier = Modifier.size(40.dp))
        }
    }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))
                .background(buttonBackground),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "저장하기",
                style = HousTheme.typography.b1,
                color = colorResource(id = R.color.hous_white),
                modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(bottom = 9.dp)
            )
        }
    }
}
