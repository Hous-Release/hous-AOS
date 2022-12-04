package hous.release.android.util

import android.content.Context
import android.widget.Toast

object ToastMessageUtil {
    private var toast: Toast? = null

    fun showToast(context: Context, msg: String) {
        toast?.cancel()
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        requireNotNull(toast).show()
    }
}
