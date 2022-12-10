package hous.release.android.presentation.profile.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.util.extension.addSourceList
import hous.release.domain.usecase.PutProfileEditUseCase
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val putProfileEditUseCase: PutProfileEditUseCase
) : ViewModel() {
    private val originData = MutableLiveData<ProfileEntity>()

    val nickname = MutableLiveData<String>()

    val birthday = MutableLiveData("")

    private val isBirthdayPublic = MutableLiveData(false)

    val mbti = MutableLiveData<String?>()

    val job = MutableLiveData<String?>()

    val introduction = MutableLiveData<String?>()

    private val _isEditProfile = MutableLiveData<Boolean>()
    val isEditProfile: LiveData<Boolean> = _isEditProfile

    private val _changedEditInfo = MediatorLiveData<Boolean>().apply {
        addSourceList(nickname, birthday, isBirthdayPublic, mbti, job, introduction) {
            checkChanged()
        }
    }
    val changedEditInfo: LiveData<Boolean> = _changedEditInfo

    private fun checkChanged(): Boolean =
        originData.value!!.nickname != nickname.value ||
            originData.value!!.birthday != birthday.value ||
            originData.value!!.birthdayPublic != isBirthdayPublic.value ||
            originData.value!!.mbti != mbti.value ||
            originData.value!!.job != job.value ||
            originData.value!!.introduction != introduction.value

    fun onClickSave() {
        viewModelScope.launch {
            putProfileEditUseCase(
                birthday = requireNotNull(birthday.value!!.replace("/", "-")),
                introduction = introduction.value,
                isPublic = requireNotNull(isBirthdayPublic.value),
                job = job.value,
                mbti = mbti.value,
                nickname = requireNotNull(nickname.value)
            ).onSuccess { isSuccess ->
                _isEditProfile.value = isSuccess
            }.onFailure { Timber.e(it.message) }
        }
    }

    fun initSelectedBirthDate(birth: String) {
        birthday.value = birth.replace("-", "/")
    }

    fun initBirthdayPublic(checked: Boolean) {
        isBirthdayPublic.value = checked
    }

    fun initData(
        profileData: ProfileEntity
    ) {
        originData.value = ProfileEntity(
            nickname = profileData.nickname,
            birthday = profileData.birthday.replace(".", "/"),
            birthdayPublic = profileData.birthdayPublic,
            mbti = profileData.mbti,
            job = profileData.job,
            introduction = profileData.introduction
        )
        nickname.value = profileData.nickname
        birthday.value = profileData.birthday.replace(".", "/")
        isBirthdayPublic.value = profileData.birthdayPublic
        mbti.value = profileData.mbti
        job.value = profileData.job
        introduction.value = profileData.introduction
    }
}
