package hous.release.android.presentation.our_rules.model

import android.os.Parcelable
import hous.release.domain.entity.Photo
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailRuleUiModel(
    val id: Int = -1,
    val name: String = "",
    val description: String = "",
    val photos: List<PhotoUiModel> = emptyList(),
    val updatedAt: String = ""
) : Parcelable

@Suppress("DataClassPrivateConstructor")
@Parcelize
data class PhotoUiModel private constructor(
    val url: String = "",
    val filePath: String? = null
) : Parcelable {

    companion object {
        fun from(photo: Photo) = when (photo) {
            is Photo.Remote -> PhotoUiModel(url = photo.path)
            is Photo.Media -> PhotoUiModel(filePath = photo.path)
            is Photo.Cache -> PhotoUiModel(filePath = photo.path)
        }
    }
}
