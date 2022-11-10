package hous.release.android.presentation.withdraw

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.FeedbackType
import hous.release.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private var feedbackType = FeedbackType.NO

    val isCheckedWithdraw = MutableStateFlow(false)

    fun initFeedbackType(type: FeedbackType) {
        feedbackType = type
    }

    fun deleteUser() {
        Timber.d(feedbackType.type)
    }
}
