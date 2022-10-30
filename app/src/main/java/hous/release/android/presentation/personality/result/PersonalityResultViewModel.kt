package hous.release.android.presentation.personality.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.response.PersonalityResult
import hous.release.domain.repository.PersonalityRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PersonalityResultViewModel @Inject constructor(
    private val personalityRepository: PersonalityRepository
) : ViewModel() {
    private val _resultData = MutableLiveData<PersonalityResult>()
    val resultData: LiveData<PersonalityResult> = _resultData

    private val _description = MutableLiveData("")
    val description: LiveData<String> = _description

    private val _fromTestResult = MutableLiveData<Boolean>()
    val fromTestResult: LiveData<Boolean> = _fromTestResult

    init {
        viewModelScope.launch {
            personalityRepository.getPersonalityResult("BLUE")
                .onSuccess { result ->
                    _resultData.value = result
                    initDescription(result.description)
                }.onFailure {
                    Timber.e(it.message)
                }
        }
    }

    private fun initDescription(list: List<String>) {
        _description.value = list.joinToString(ENTER)
    }

    fun initFromTestResult(fromTestResult: Boolean) {
        _fromTestResult.value = fromTestResult
    }

    companion object {
        private const val ENTER = "\n"
    }
}
