package hous.release.android.presentation.personality.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.PersonalityTest
import hous.release.domain.entity.QuestionType
import hous.release.domain.usecase.GetPersonalityTestsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PersonalityTestViewModel @Inject constructor(
    private val getPersonalityTestsUseCase: GetPersonalityTestsUseCase,
    private val putPersonalityTestsUseCase: GetPersonalityTestsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(PersonalityTestUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getPersonalityTestsUseCase()
                .onSuccess { personalityTests ->
                    Timber.d(personalityTests.toString())
                    _uiState.update { uiState ->
                        uiState.copy(personalityTests = personalityTests)
                    }
                }
                .onFailure {
                    Timber.e(it.message)
                }
        }
    }
}

data class PersonalityTestUiState(
    val personalityTests: List<PersonalityTest> = emptyList(),
    val testScore: HashMap<QuestionType, Int> = hashMapOf(
        QuestionType.LIGHT to 0,
        QuestionType.NOISE to 0,
        QuestionType.CLEAN to 0,
        QuestionType.SMELL to 0,
        QuestionType.INTROVERSION to 0
    )
)
