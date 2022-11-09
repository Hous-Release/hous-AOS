package hous.release.data.datasource

import hous.release.data.entity.response.BaseResponse
import hous.release.data.entity.response.ProfileResponse
import hous.release.data.service.ProfileService
import javax.inject.Inject

class ProfileDataSource @Inject constructor(
    private val profileService: ProfileService
) {
    suspend fun getProfile(): BaseResponse<ProfileResponse> =
        profileService.getProfile()

    suspend fun getHomieProfile(): BaseResponse<ProfileResponse> =
        profileService.getHomieProfile()
}
