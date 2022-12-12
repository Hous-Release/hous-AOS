package hous.release.android.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.response.VersionInfo
import hous.release.domain.repository.VersionRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val versionRepository: VersionRepository
) : ViewModel() {
    private val _versionInfo = MutableSharedFlow<VersionInfo>()
    val versionInfo: SharedFlow<VersionInfo> = _versionInfo.asSharedFlow()

    private val isAnimatorDone = MutableSharedFlow<Boolean>()

    val isSuccessUiEvent = combine(
        versionInfo,
        isAnimatorDone
    ) { version, isDone -> !version.needsForceUpdate == isDone }

    fun initIsAnimatorDone() {
        viewModelScope.launch { isAnimatorDone.emit(true) }
    }

    fun getVersionCheck() {
        viewModelScope.launch {
            versionRepository.getVersionCheck()
                .onSuccess { response ->
                    Timber.d(response.toString())
                    _versionInfo.emit(response)
                }
                .onFailure { Timber.d(it.message.toString()) }
        }
    }
}
