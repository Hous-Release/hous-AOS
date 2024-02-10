package hous.release.android.presentation.our_rules.component.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import hous.release.android.R
import hous.release.designsystem.theme.HousTheme

@Composable
fun RuleGuideLottie(
    idx: Int
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            provideRuleGuideLottie(idx)
        )
    )

    Box(
        modifier = Modifier
            .size(122.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
    }
}

@Throws(IllegalArgumentException::class)
private fun provideRuleGuideLottie(idx: Int): Int {
    return when (idx) {
        0 -> R.raw.rule_guide_1
        1 -> R.raw.rule_guide_2
        2 -> R.raw.rule_guide_3
        else -> throw IllegalArgumentException("Invalid Index: $idx")
    }
}

@Composable
@Preview(showBackground = true)
private fun RuleGuideBottomSheetContentPreview() {
    HousTheme {
        RuleGuideBottomSheetContent()
    }
}
