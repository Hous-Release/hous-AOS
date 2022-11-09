package hous.release.android.presentation.profile.homie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.response.Profile
import hous.release.domain.usecase.GetHomieProfileUseCase
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomieProfileViewModel @Inject constructor(
    private val getHomieProfileUseCase: GetHomieProfileUseCase
) : ViewModel() {
    private val _homieProfileData = MutableLiveData<Profile>()
    val homieProfileData: LiveData<Profile> = _homieProfileData

    init {
        viewModelScope.launch {
            getHomieProfileUseCase.invoke()
                .onSuccess { response ->
                    _homieProfileData.value = response
                }.onFailure { response ->
                    Timber.e(response.message)
                }
        }
    }
}
