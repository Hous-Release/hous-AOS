package hous.release.android.presentation.personality.test

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.PersonalityTest
import hous.release.domain.entity.QuestionType
import hous.release.domain.usecase.GetPersonalityTestsUseCase
import javax.inject.Inject

@HiltViewModel
class PersonalityTestViewModel @Inject constructor(
    private val getPersonalityTestsUseCase: GetPersonalityTestsUseCase,
    private val putPersonalityTestsUseCase: GetPersonalityTestsUseCase
) : ViewModel()

data class PersonalityTestUiState(
    val personalityTests: List<PersonalityTest>,
    val testScore: HashMap<QuestionType, Int> = hashMapOf(
        QuestionType.LIGHT to 0,
        QuestionType.NOISE to 0,
        QuestionType.CLEAN to 0,
        QuestionType.SMELL to 0,
        QuestionType.INTROVERSION to 0
    )
)
