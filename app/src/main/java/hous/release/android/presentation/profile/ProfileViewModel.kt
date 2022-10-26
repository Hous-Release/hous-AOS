package hous.release.android.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {
    private val _age = MutableLiveData<String>()
    val age: LiveData<String> = _age

    private val _birthday = MutableLiveData<String>()
    val birthday: LiveData<String> = _birthday

    private val _introduction = MutableLiveData<String>()
    val introduction: LiveData<String> = _introduction

    private val _job = MutableLiveData<String>()
    val job: LiveData<String> = _job

    private val _mbti = MutableLiveData<String>()
    val mbti: LiveData<String> = _mbti

    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> = _nickname

    private val _personalityColor = MutableLiveData<String>()
    val personalityColor: LiveData<String> = _personalityColor

    private val _isTest = MutableLiveData<Boolean>()
    val isTest: LiveData<Boolean> = _isTest

    init {
        viewModelScope.launch {
            _age.value = "23세"
            _job.value = "개백수"
            _nickname.value = "이빵주"
            _personalityColor.value = "GRAY"
            _isTest.value = _personalityColor.value != "GRAY"
        }
    }
}
