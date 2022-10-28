package hous.release.android.util.dialog

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DatePickerClickListener(
    val confirmActionWithDate: (String) -> Unit = {}
) : Parcelable {
    fun onConfirmClick(date: String) {
        confirmActionWithDate(date)
    }
}
