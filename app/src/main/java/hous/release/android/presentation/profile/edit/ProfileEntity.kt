package hous.release.android.presentation.profile.edit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileEntity(
    val nickname: String,
    val birthday: String,
    val birthdayPublic: Boolean,
    val mbti: String?,
    val job: String?,
    val introduction: String?
) : Parcelable
