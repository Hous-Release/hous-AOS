package hous.release.android.presentation.badge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.BadgeState
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
    private val selectedBadgeIndex = MutableStateFlow(-1)

    init {
        viewModelScope.launch {
            getBadges()
        }
    }

    suspend fun getBadges() {
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

    fun selectBadge(badgeIndex: Int) {
        val badges = uiState.value.badges.toMutableList()
        if (selectedBadgeIndex.value != -1) {
            badges[selectedBadgeIndex.value] =
                badges[selectedBadgeIndex.value].copy(badgeState = BadgeState.UNLOCK)
        }
        badges[badgeIndex] = badges[badgeIndex].copy(badgeState = BadgeState.CHECKED)
        selectedBadgeIndex.value = badgeIndex
        _uiState.value = _uiState.value.copy(badges = badges)
    }

    fun changeRepresentBadge(badgeId: Int) {
        viewModelScope.launch {
            badgeRepository.changeRepresentBadge(badgeId)
            getBadges()
            selectedBadgeIndex.value = -1
        }
    }

    fun unLockBadges() {
        if (selectedBadgeIndex.value != -1) {
            val badges = uiState.value.badges.toMutableList()
            badges[selectedBadgeIndex.value] =
                badges[selectedBadgeIndex.value].copy(badgeState = BadgeState.UNLOCK)
            selectedBadgeIndex.value = -1
            _uiState.value = _uiState.value.copy(badges = badges)
        }
    }
}
