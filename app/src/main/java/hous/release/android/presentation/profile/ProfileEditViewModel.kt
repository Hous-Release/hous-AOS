package hous.release.android.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.repository.ProfileRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {
    val nickname = MutableLiveData<String>()

    val birthday = MutableLiveData("")

    val isBirthdayPublic = MutableLiveData<Boolean>()

    val mbti = MutableLiveData<String>()

    val job = MutableLiveData<String>()

    val introduction = MutableLiveData<String>()

    private val _saveBtn = MutableLiveData<Boolean>()
    val saveBtn: LiveData<Boolean> = _saveBtn

    private val _isSuccessEditProfile = MutableLiveData<Boolean>()
    val isSuccessEditProfile: LiveData<Boolean> = _isSuccessEditProfile

    fun putProfile() {
        viewModelScope.launch {
            profileRepository.putProfile(
                birthday = requireNotNull(birthday.value),
                introduction = introduction.value,
                isPublic = requireNotNull(isBirthdayPublic.value),
                job = job.value,
                mbti = mbti.value,
                nickname = requireNotNull(nickname.value)
            ).onSuccess { isSuccess ->
                _isSuccessEditProfile.value = isSuccess
            }.onFailure { Timber.e(it.message) }
        }
    }
}
