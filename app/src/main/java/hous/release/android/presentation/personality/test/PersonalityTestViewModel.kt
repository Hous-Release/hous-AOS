package hous.release.android.presentation.personality.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.presentation.personality.test.PersonalityTestAdapter.Companion.NEXT
import hous.release.domain.entity.PersonalityTest
import hous.release.domain.entity.QuestionType
import hous.release.domain.usecase.GetPersonalityTestsUseCase
import hous.release.domain.usecase.PutPersonalityTestResult
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class PersonalityTestViewModel @Inject constructor(
    private val getPersonalityTestsUseCase: GetPersonalityTestsUseCase,
    private val putPersonalityTestResultUseCase: PutPersonalityTestResult
) : ViewModel() {
    private val _uiState = MutableStateFlow(PersonalityTestUiState())
    val uiState = _uiState.asStateFlow()
    private val _moveEvent = MutableSharedFlow<PersonalityTestEvent>()
    val moveEvent = _moveEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            getPersonalityTestsUseCase()
                .onSuccess { personalityTests ->
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
        _uiState.update { uiState ->
            val newPersonalityTest = uiState.personalityTests.map { personalityTest ->
                if (selectPersonalityTest.index == personalityTest.index) {
                    return@map selectPersonalityTest
                }
                personalityTest
            }
            uiState.copy(personalityTests = newPersonalityTest)
        }
        calculateTestScore(selectPersonalityTest)

        if (selectPersonalityTest.index == 15) onEvent(PersonalityTestEvent.Loading)
        else onEvent(PersonalityTestEvent.MovePage(NEXT))
    }

    private fun calculateTestScore(selectPersonalityTest: PersonalityTest) {
        uiState.value.testScore[selectPersonalityTest.index - 1] =
            TestHolder(selectPersonalityTest.questionType, selectPersonalityTest.testState.ordinal)
    }

    fun onEvent(personalityTestEvent: PersonalityTestEvent) {
        viewModelScope.launch { _moveEvent.emit(personalityTestEvent) }
    }

    fun putPersonalityTestResult() {
        val sumScore: HashMap<QuestionType, Int> = hashMapOf(
            QuestionType.LIGHT to 0,
            QuestionType.CLEAN to 0,
            QuestionType.SMELL to 0,
            QuestionType.NOISE to 0,
            QuestionType.INTROVERSION to 0
        )
        uiState.value.testScore.forEach { testHolder ->
            sumScore[testHolder.questionType] =
                sumScore[testHolder.questionType]!! + testHolder.score
        }
        viewModelScope.launch {
            putPersonalityTestResultUseCase(
                cleanScore = requireNotNull(sumScore[QuestionType.CLEAN]),
                introversionScore = requireNotNull(sumScore[QuestionType.INTROVERSION]),
                lightScore = requireNotNull(sumScore[QuestionType.LIGHT]),
                noiseScore = requireNotNull(sumScore[QuestionType.NOISE]),
                smellScore = requireNotNull(sumScore[QuestionType.SMELL])
            )
                .onSuccess { resultColor ->
                    delay(1000)
                    onEvent(PersonalityTestEvent.GoToResultView(resultColor))
                }
                .onFailure {
                    Timber.e(it.message)
                }
        }
    }
}

data class PersonalityTestUiState(
    val personalityTests: List<PersonalityTest> = emptyList(),
    val testScore: MutableList<TestHolder> = mutableListOf<TestHolder>().apply {
        for (i in 0 until 15) add(TestHolder())
    }
)

data class TestHolder(
    val questionType: QuestionType = QuestionType.LIGHT,
    val score: Int = -1
)

sealed class PersonalityTestEvent {
    object Loading : PersonalityTestEvent()
    data class MovePage(val direction: Int) : PersonalityTestEvent()
    data class GoToResultView(val testResultColor: String) : PersonalityTestEvent()
}
