package hous.release.android.util.dialog

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class DatePickerClickListener(
    val confirmActionWithDate: (String) -> Unit
) : Parcelable {
    fun onConfirmClick(date: String) {
        confirmActionWithDate(date)
    }
}
