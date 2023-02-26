package hous.release.android.presentation.personality.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.response.PersonalityResult
import hous.release.domain.usecase.GetPersonalityResultUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PersonalityResultViewModel @Inject constructor(
    private val getPersonalityResultUseCase: GetPersonalityResultUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(PersonalityResultUiState())
    val uiState = _uiState.asStateFlow()

    fun getPersonalityResult(personalityColor: String) {
        viewModelScope.launch {
            getPersonalityResultUseCase(personalityColor)
                .onSuccess { response ->
                    _uiState.value = _uiState.value.copy(
                        personalityResult = response
                    )
                }.onFailure {
                    Timber.e(it.message)
                }
        }
    }

    fun initFromTestResult(fromTestResult: Boolean) {
        _uiState.value = PersonalityResultUiState().copy(
            fromTestResult = fromTestResult
        )
    }

    data class PersonalityResultUiState(
        val personalityResult: PersonalityResult = PersonalityResult(),
        val fromTestResult: Boolean = false
    )
}
