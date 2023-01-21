package hous.release.android.presentation.profile.homie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hous.release.domain.entity.response.Profile
import hous.release.domain.usecase.GetHomieProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomieProfileViewModel @Inject constructor(
    private val getHomieProfileUseCase: GetHomieProfileUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(Profile())
    val uiState = _uiState.asStateFlow()

    fun getHomieProfile(homieId: Int) {
        viewModelScope.launch {
            getHomieProfileUseCase(homieId)
                .onSuccess { response ->
                    _uiState.value = _uiState.value.copy(
                        age = response.age,
                        birthday = response.birthday.substring(5..9),
                        birthdayPublic = response.birthdayPublic,
                        introduction = response.introduction,
                        job = response.job,
                        mbti = response.mbti,
                        nickname = response.nickname,
                        personalityColor = response.personalityColor,
                        representBadge = response.representBadge,
                        representBadgeImage = response.representBadgeImage,
                        testScore = response.testScore
                    )
                }.onFailure { response ->
                    Timber.e(response.message)
                }
        }
    }
}
