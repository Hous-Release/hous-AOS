package hous.release.android.presentation.our_rules.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailRuleUiModel(
    val id: Int = -1,
    val name: String = "",
    val description: String = "",
    val images: List<PhotoUiModel> = emptyList(),
    val updatedAt: String = ""
) : Parcelable

@Parcelize
data class PhotoUiModel(
    val url: String = "",
    val filePath: String? = null
) : Parcelable
