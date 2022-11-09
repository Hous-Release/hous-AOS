package hous.release.android.presentation.personality.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.presentation.personality.test.PersonalityTestAdapter.Companion.NEXT
import hous.release.domain.entity.PersonalityTest
import hous.release.domain.entity.QuestionType
import hous.release.domain.usecase.GetPersonalityTestsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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
    private val _moveEvent = MutableSharedFlow<Int>()
    val moveEvent = _moveEvent.asSharedFlow()

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

    fun setTestState(selectPersonalityTest: PersonalityTest) {
        calculateTestScore(selectPersonalityTest)
        viewModelScope.launch {
            _uiState.update { uiState ->
                val newPersonalityTest = uiState.personalityTests.map { personalityTest ->
                    if (selectPersonalityTest.index == personalityTest.index) {
                        return@map selectPersonalityTest
                    }
                    personalityTest
                }
                uiState.copy(personalityTests = newPersonalityTest)
            }

            delay(300L)
            _moveEvent.emit(NEXT)
        }
    }

    private fun calculateTestScore(selectPersonalityTest: PersonalityTest) {
        val currentScore: Int =
            requireNotNull(uiState.value.testScore[selectPersonalityTest.questionType])
        uiState.value.testScore[selectPersonalityTest.questionType] =
            currentScore + selectPersonalityTest.testState.ordinal
    }

    fun movePage(move: Int) {
        viewModelScope.launch { _moveEvent.emit(move) }
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
