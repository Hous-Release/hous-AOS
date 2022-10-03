package hous.release.android.presentation.hous

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.response.Homy
import hous.release.domain.entity.response.Hous
import hous.release.domain.repository.HousRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HousViewModel @Inject constructor(
    private val housRepository: HousRepository
) : ViewModel() {
    private val _hous = MutableStateFlow(Hous())
    val hous: StateFlow<Hous> = _hous.asStateFlow()

    private val _homies = MutableStateFlow(emptyList<Homy>())
    val homies: StateFlow<List<Homy>> = _homies.asStateFlow()

    fun getHome() {
        viewModelScope.launch {
            housRepository.getHome()
                .onSuccess { response ->
                    _hous.value = response
                    _homies.value = response.homies
                }
                .onFailure { Timber.d(it.message.toString()) }
        }
    }
}
