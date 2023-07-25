package hous.release.android.presentation.our_rules.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailRuleUiModel(
    val id: Int = -1,
    val name: String = "",
    val description: String = "",
    val images: List<String> = emptyList(),
    val updatedAt: String = ""
) : Parcelable
