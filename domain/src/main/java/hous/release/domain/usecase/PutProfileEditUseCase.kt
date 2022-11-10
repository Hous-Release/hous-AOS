package hous.release.domain.usecase

import hous.release.domain.repository.ProfileRepository
import javax.inject.Inject

class PutProfileEditUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(
        birthday: String,
        introduction: String?,
        isPublic: Boolean,
        job: String?,
        mbti: String?,
        nickname: String
    ) = profileRepository.putProfile(birthday, introduction, isPublic, job, mbti, nickname)
}
