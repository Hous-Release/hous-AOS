package hous.release.android.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.response.Profile
import hous.release.domain.usecase.GetProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    fun getProfile() {
        viewModelScope.launch {
            getProfileUseCase()
                .onSuccess { response ->
                    _uiState.value = _uiState.value.copy(
                        profile = response,
                        isLoadingState = false,
                        birthday = response.birthday.substring(5..9)
                    )
                    checkTest()
                }.onFailure { response ->
                    Timber.e(response.message)
                    _uiState.value = uiState.value.copy(
                        isLoadingState = false
                    )
                }
        }
    }

    private fun checkTest() {
        _uiState.value = _uiState.value.copy(
            isTest = _uiState.value.profile.personalityColor != HomyType.GRAY
        )
    }

    data class ProfileUiState(
        val profile: Profile = Profile(),
        val birthday: String = "",
        val isTest: Boolean = false,
        val isLoadingState: Boolean = true
    )
}
