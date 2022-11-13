package hous.release.data.datasource

import hous.release.data.entity.request.ProfileEditRequest
import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.NoDataResponse
import hous.release.data.entity.response.ProfileResponse
import hous.release.data.service.ProfileService
import javax.inject.Inject

class ProfileDataSource @Inject constructor(
    private val profileService: ProfileService
) {
    suspend fun getProfile(): BaseResponse<ProfileResponse> =
        profileService.getProfile()

    suspend fun putProfile(
        birthday: String,
        introduction: String?,
        isPublic: Boolean,
        job: String?,
        mbti: String?,
        nickname: String
    ): NoDataResponse =
        profileService.putProfile(
            ProfileEditRequest(
                birthday = birthday,
                introduction = introduction,
                isPublic = isPublic,
                job = job,
                mbti = mbti,
                nickname = nickname
            )
        )

    suspend fun getHomieProfile(homieId: Int): BaseResponse<ProfileResponse> =
        profileService.getHomieProfile(homieId)
}
