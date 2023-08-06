package hous.release.domain.usecase

import hous.release.domain.repository.SettingsRepository
import javax.inject.Inject

class PostWithdrawFeedbackUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(comment: String) =
        settingsRepository.postWithdrawFeedback(comment = comment)
}
