package hous.release.android.util.extension

import androidx.fragment.app.DialogFragment
import hous.release.android.R
import timber.log.Timber

fun DialogFragment.initLayout() {
    safeLet(dialog, requireNotNull(dialog).window) { dialog, window ->
        window.apply {
            isCancelable = false
            setBackgroundDrawableResource(R.drawable.shape_white_fill_8_rect)
        }
    } ?: Timber.e(
        getString(R.string.null_point_exception_detail_two_item).format(
            "dialog",
            dialog == null,
            "window",
            dialog!!.window == null
        )
    )
}
