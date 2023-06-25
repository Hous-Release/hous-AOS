package hous.release.android.util

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast

object BuildUtils {
    /* API 33 - Android 13 (Tiramisu) */
    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
    fun isBundleValuesSafeToCast(): Boolean =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
    fun isNotificationRuntimePermissionNeeded(): Boolean =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

    /* API 31 - Android 12 (Snow Cone) */
    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
    fun isDynamicColorSupported(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
}

// https://medium.com/@vtsen/requiresapi-and-checkssdkintatleast-annotations-1469e31b828c
