package hous.release.android.presentation.badge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.response.BadgeContent
import hous.release.domain.repository.BadgeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BadgeViewModel @Inject constructor(
    private val badgeRepository: BadgeRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<BadgeContent> =
        MutableStateFlow(
            BadgeContent(
                representBadge = null,
                badges = emptyList()
            )
        )
    val uiState: StateFlow<BadgeContent> = _uiState.asStateFlow()

    init {
        getBadges()
    }

    fun getBadges() {
        viewModelScope.launch {
            badgeRepository.getBadges()
                .onSuccess { badges ->
                    _uiState.value = _uiState.value.copy(
                        representBadge = badges.representBadge,
                        badges = badges.badges
                    )
                }
                .onFailure {
                    Timber.d("data ${it.message}")
                }
        }
    }
}
