package hous.release.android.presentation.profile.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.usecase.PutProfileEditUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val putProfileEditUseCase: PutProfileEditUseCase
) : ViewModel() {
    private val originData = MutableStateFlow(ProfileEntity())

    val nickname = MutableStateFlow("")
    val birthday = MutableStateFlow("")
    val isPrivateBirthday = MutableStateFlow(false)
    val mbti = MutableStateFlow<String?>("")
    val job = MutableStateFlow<String?>("")
    val introduction = MutableStateFlow<String?>("")

    private val _isProfileEdit = MutableSharedFlow<Boolean>()
    val isProfileEdit = _isProfileEdit.asSharedFlow()

    private val userBasicInfo = combine(
        nickname, birthday, isPrivateBirthday
    ) { nickname, birthday, isBirthdayPublic ->
        Triple(nickname, birthday, isBirthdayPublic)
    }

    private val userAdditionalInfo = combine(
        mbti, job, introduction
    ) { mbti, job, introduction ->
        Triple(mbti, job, introduction)
    }

    val isProfileChanged = combine(
        userBasicInfo, userAdditionalInfo, originData
    ) { userBasicInfo, userAdditionalInfo, originData ->
        originData.let {
            it.nickname != userBasicInfo.first || it.birthday != userBasicInfo.second || it.birthdayPublic != userBasicInfo.third || it.mbti != userAdditionalInfo.first || it.job != userAdditionalInfo.second || it.introduction != userAdditionalInfo.third
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun onClickSave() {
        viewModelScope.launch {
            putProfileEditUseCase(
                birthday = requireNotNull(birthday.value.replace("/", "-")),
                introduction = introduction.value,
                isPublic = !requireNotNull(isPrivateBirthday.value),
                job = job.value,
                mbti = mbti.value,
                nickname = requireNotNull(nickname.value)
            ).onSuccess { success ->
                _isProfileEdit.emit(success)
            }.onFailure { Timber.e(it.message) }
        }
    }

    fun initSelectedBirthDate(birth: String) {
        birthday.value = birth.replace("-", "/")
    }

    fun isPrivateBirthday(checked: Boolean) {
        isPrivateBirthday.value = checked
    }

    fun initData(profileData: ProfileEntity) {
        originData.value = ProfileEntity(
            nickname = profileData.nickname,
            birthday = profileData.birthday.replace(".", "/"),
            birthdayPublic = !profileData.birthdayPublic,
            mbti = profileData.mbti,
            job = profileData.job,
            introduction = profileData.introduction
        )
        nickname.value = profileData.nickname
        birthday.value = profileData.birthday.replace(".", "/")
        isPrivateBirthday.value = !profileData.birthdayPublic
        mbti.value = profileData.mbti
        job.value = profileData.job
        introduction.value = profileData.introduction
    }
}
