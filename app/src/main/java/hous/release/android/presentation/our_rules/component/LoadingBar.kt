package hous.release.android.presentation.our_rules.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import hous.release.android.R
import hous.release.designsystem.theme.HousBlack
import hous.release.designsystem.theme.HousTheme
import kotlinx.coroutines.delay

@Composable
fun LoadingBar(
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.loading)
    )

    Box(
        modifier = Modifier.zIndex(1f)
            .background(HousBlack.copy(alpha = 0.5f))
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onPress = { /*Interaction 막기*/ })
            },
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            modifier = modifier.padding(start = 87.dp, end = 87.dp),
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
    }
}

@Preview("Loading 화면 예시")
@Composable
private fun LoadingPreview() {
    HousTheme {
        var isShowLoading by remember { mutableStateOf(true) }
        LaunchedEffect(Unit) {
            delay(3000)
            isShowLoading = false
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = " Hello World", color = HousBlack)
        }
        if (isShowLoading) LoadingBar()
    }
}
