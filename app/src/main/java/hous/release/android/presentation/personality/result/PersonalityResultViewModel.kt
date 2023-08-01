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
    private val _uiState = MutableStateFlow(PersonalityResult())
    val uiState = _uiState.asStateFlow()

    fun getPersonalityResult(personalityColor: String) {
        viewModelScope.launch {
            getPersonalityResultUseCase(personalityColor)
                .onSuccess { response ->
                    _uiState.value = response
                }.onFailure {
                    Timber.e(it.message)
                }
        }
    }
}
