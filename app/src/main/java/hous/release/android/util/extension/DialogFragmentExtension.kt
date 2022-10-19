package hous.release.android.util.extension

import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hous.release.android.R
import timber.log.Timber

fun DialogFragment.initLayout(ratio: Double = 0.82) {
    safeLet(dialog, requireNotNull(dialog).window) { _, window ->
        window.apply {
            isCancelable = false
            setLayout(
                (resources.displayMetrics.widthPixels * ratio).toInt(),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
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
