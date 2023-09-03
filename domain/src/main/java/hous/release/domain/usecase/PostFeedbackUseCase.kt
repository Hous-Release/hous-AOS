package hous.release.domain.usecase

import hous.release.domain.repository.SettingsRepository
import javax.inject.Inject

class PostFeedbackUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(comment: String, isDeleting: Boolean) =
        settingsRepository.postFeedback(comment = comment, isDeleting = isDeleting)
}
