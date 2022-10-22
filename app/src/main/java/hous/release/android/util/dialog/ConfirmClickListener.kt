package hous.release.android.util.dialog

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ConfirmClickListener(val confirmAction: () -> Unit) : Parcelable {
    fun onConfirmClick() = confirmAction()
}
