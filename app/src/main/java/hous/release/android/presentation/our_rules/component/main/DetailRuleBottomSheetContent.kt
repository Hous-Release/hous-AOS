package hous.release.android.presentation.our_rules.component.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hous.release.android.presentation.our_rules.component.PhotoGrid
import hous.release.android.presentation.our_rules.model.DetailRuleUiModel
import hous.release.designsystem.component.HousDetailBottomSheet
import hous.release.designsystem.theme.HousBlack
import hous.release.designsystem.theme.HousG4
import hous.release.designsystem.theme.HousG6
import hous.release.designsystem.theme.HousTheme

@Composable
fun DetailRuleBottomSheetContent(
    detailRule: DetailRuleUiModel = DetailRuleUiModel(),
    onNavigateToUpdateRule: (DetailRuleUiModel) -> Unit = {},
    onDeleteRule: (Int) -> Unit = {}
) {
    val photos = detailRule.photos
    HousDetailBottomSheet(
        modifier = Modifier.padding(top = 26.dp),
        editAction = { onNavigateToUpdateRule(detailRule) },
        deleteAction = { onDeleteRule(detailRule.id) }
    ) {
        if (photos.isNotEmpty()) {
            PhotoGrid(
                photos = photos,
                isEditable = false,
                photoFraction = 0.62f,
                spaceWidthDp = 14.dp,
                edgeWidthDp = 67.dp
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            Text(text = detailRule.name, style = HousTheme.typography.h3, color = HousBlack)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = detailRule.updatedAt.formatDate(),
                style = HousTheme.typography.en2,
                color = HousG4
            )
            Spacer(modifier = Modifier.height(7.dp))
            if (detailRule.description.isEmpty()) {
                Text(
                    text = detailRule.description,
                    style = HousTheme.typography.b2,
                    color = HousG4
                )
                Spacer(modifier = Modifier.height(12.dp))
            } else {
                Text(
                    text = detailRule.description,
                    style = HousTheme.typography.b2,
                    color = HousG6
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

private fun String.formatDate() = "마지막 수정 $this"

@Preview(showBackground = true, name = "no description")
@Composable
fun PreviewDetailRuleBottomSheetContent() {
    HousTheme {
        DetailRuleBottomSheetContent(
            detailRule = DetailRuleUiModel(
                name = "바나나는 옷걸이에 걸어두기!",
                updatedAt = "2023.04.01"
            )
        )
    }
}

@Preview(showBackground = true, name = "with description")
@Composable
fun PreviewDetailRuleBottomSheetContent2() {
    HousTheme {
        DetailRuleBottomSheetContent(
            detailRule = DetailRuleUiModel(
                name = "바나나는 옷걸이에 걸어두기!",
                updatedAt = "2023.04.01",
                description = "옷걸이에 걸어두면 바나나는 자기가 아직도 나무에 걸려있다고 착각하고 싱싱한 상태를 유지한대.. 귀여워서 기절"
            )
        )
    }
}
