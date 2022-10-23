package hous.release.android.util.dialog

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ConfirmClickListener(
    val id: Int = -1,
    val confirmAction: () -> Unit = {},
    val confirmActionWithId: (Int) -> Unit = {}
) : Parcelable {
    fun onConfirmClick() {
        if (id == -1) {
            confirmAction()
        } else {
            confirmActionWithId(id)
        }
    }
}
