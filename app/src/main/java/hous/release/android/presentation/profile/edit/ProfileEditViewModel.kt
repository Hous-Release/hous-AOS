package hous.release.android.presentation.profile.edit

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

    private val _isEditProfile = MutableLiveData<Boolean>()
    val isEditProfile: LiveData<Boolean> = _isEditProfile

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
                _isEditProfile.value = isSuccess
            }.onFailure { Timber.e(it.message) }
        }
    }

    fun initSelectedBirthDate(birth: String) {
        birthday.value = birth
    }

    fun initBirthdayPublic(checked: Boolean) {
        _isBirthdayPublic.value = checked
    }

    fun initData(
        profileData: ProfileEntity
    ) {
        nickname.value = profileData.nickname
        birthday.value = profileData.birthday.replace(DOT, DASH)
        _isBirthdayPublic.value = profileData.birthdayPublic
        mbti.value = profileData.mbti
        job.value = profileData.job
        introduction.value = profileData.introduction
    }

    companion object {
        private const val DOT = "."
        private const val DASH = "-"
    }
}
