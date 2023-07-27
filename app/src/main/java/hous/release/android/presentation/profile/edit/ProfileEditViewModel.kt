package hous.release.android.presentation.profile.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.android.util.extension.combine
import hous.release.domain.usecase.PutProfileEditUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _isProfileChanged = MutableStateFlow(false)
    val isProfileChanged = _isProfileChanged.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                nickname,
                birthday,
                isPrivateBirthday,
                mbti,
                job,
                introduction,
                originData
            ) { nickname, birthday, isBirthdayPublic, mbti, job, introduction, originData ->
                originData.nickname != nickname ||
                        originData.birthday != birthday ||
                        originData.birthdayPublic != isBirthdayPublic ||
                        originData.mbti != mbti ||
                        originData.job != job ||
                        originData.introduction != introduction
            }.collect { value ->
                _isProfileChanged.value = value
            }
        }
    }

    fun updateProfile() {
        viewModelScope.launch {
            putProfileEditUseCase(
                birthday = birthday.value.replace("/", "-"),
                introduction = introduction.value,
                isPublic = !isPrivateBirthday.value,
                job = job.value,
                mbti = mbti.value,
                nickname = nickname.value
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
            mbti = profileData.mbti ?: "",
            job = profileData.job ?: "",
            introduction = profileData.introduction ?: ""
        )
        nickname.value = profileData.nickname
        birthday.value = profileData.birthday.replace(".", "/")
        isPrivateBirthday.value = !profileData.birthdayPublic
        mbti.value = profileData.mbti ?: ""
        job.value = profileData.job ?: ""
        introduction.value = profileData.introduction ?: ""
    }
}
