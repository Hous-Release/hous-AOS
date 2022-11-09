package hous.release.android.presentation.personality.test

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.usecase.GetPersonalityTestsUseCase
import javax.inject.Inject

@HiltViewModel
class PersonalityTestViewModel @Inject constructor(
    private val getPersonalityTestsUseCase: GetPersonalityTestsUseCase,
    private val putPersonalityTestsUseCase: GetPersonalityTestsUseCase
) : ViewModel()
