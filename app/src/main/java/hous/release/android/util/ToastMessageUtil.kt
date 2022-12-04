package hous.release.android.util

import android.content.Context
import android.widget.Toast
import timber.log.Timber

object ToastMessageUtil {
    private var toast: Toast? = null

    fun showToast(context: Context, msg: String) {
        if (toast == null) {
            Timber.d("toast null")
        }
        toast?.cancel()
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        requireNotNull(toast).show()
    }
}
