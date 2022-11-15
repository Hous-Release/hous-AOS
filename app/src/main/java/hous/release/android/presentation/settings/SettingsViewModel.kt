package hous.release.android.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _isSuccessLogout = MutableSharedFlow<Boolean>()
    val isSuccessLogout: SharedFlow<Boolean> = _isSuccessLogout.asSharedFlow()

    private val _isAllowedLogout = MutableSharedFlow<Boolean>()
    val isAllowedLogout: SharedFlow<Boolean> = _isAllowedLogout.asSharedFlow()

    fun postLogout() {
        viewModelScope.launch {
            authRepository.postLogout()
                .onSuccess {
                    authRepository.clearLocalPref()
                    _isSuccessLogout.emit(true)
                }
                .onFailure { Timber.d(it.message.toString()) }
        }
    }

    fun initIsAllowedLogout(isAllowed: Boolean) {
        viewModelScope.launch { _isAllowedLogout.emit(true) }
    }
}
