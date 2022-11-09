package hous.release.android.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.response.Profile
import hous.release.domain.usecase.GetProfileUseCase
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase
) : ViewModel() {
    private val _profileData = MutableLiveData<Profile>()
    val profileData: LiveData<Profile> = _profileData

    private val _isTest = MutableLiveData<Boolean>()
    val isTest: LiveData<Boolean> = _isTest

    init {
        getProfile()
    }

    fun getProfile() {
        viewModelScope.launch {
            getProfileUseCase.invoke()
                .onSuccess { response ->
                    _profileData.value = response
                    checkTest()
                }.onFailure { response ->
                    Timber.e(response.message)
                }
        }
    }

    private fun checkTest() {
        viewModelScope.launch {
            _isTest.value = _profileData.value!!.personalityColor != HomyType.GRAY
        }
    }
}
