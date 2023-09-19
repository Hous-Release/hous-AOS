package hous.release.android.presentation.withdraw.withdrawDone

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WithdrawDoneViewModel : ViewModel() {
    private val _layout = MutableStateFlow("")
    val layout = _layout.asStateFlow()

    fun initLayout(layout: String) {
        _layout.value = layout
    }
}