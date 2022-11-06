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

    private val _isBirthdayPublic = MutableLiveData(false)
    val isBirthdayPublic: LiveData<Boolean> = _isBirthdayPublic

    val mbti = MutableLiveData<String>()

    val job = MutableLiveData<String>()

    val introduction = MutableLiveData<String>()

    private val _introductionLength = MutableLiveData(0)
    val introductionLength: LiveData<Int> = _introductionLength

    private val _isSuccessEditProfile = MutableLiveData<Boolean>()
    val isSuccessEditProfile: LiveData<Boolean> = _isSuccessEditProfile

    fun onClickSave() {
        viewModelScope.launch {
            profileRepository.putProfile(
                birthday = requireNotNull(birthday.value),
                introduction = introduction.value,
                isPublic = requireNotNull(_isBirthdayPublic.value),
                job = job.value,
                mbti = mbti.value,
                nickname = requireNotNull(nickname.value)
            ).onSuccess { isSuccess ->
                _isSuccessEditProfile.value = isSuccess
            }.onFailure { Timber.e(it.message) }
        }
    }

    fun initSelectedBirthDate(birth: String) {
        birthday.value = birth
    }

    fun initBirthdayPublic(checked: Boolean) {
        _isBirthdayPublic.value = checked
    }

    fun initIntroductionLength(length: Int) {
        _introductionLength.value = length
    }

    fun initData(
        nicknameFromProfile: String,
        birthdayFromProfile: String?,
        birthdayPublicFromProfile: Boolean,
        mbtiFromProfile: String?,
        jobFromProfile: String?,
        introductionFromProfile: String?
    ) {
        nickname.value = nicknameFromProfile
        birthday.value = birthdayFromProfile
        _isBirthdayPublic.value = birthdayPublicFromProfile
        mbti.value = mbtiFromProfile
        job.value = jobFromProfile
        introduction.value = introductionFromProfile
    }
}
