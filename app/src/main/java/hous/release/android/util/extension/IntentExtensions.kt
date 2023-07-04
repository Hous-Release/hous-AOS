package hous.release.android.util.extension

import android.content.Intent
import android.os.Parcelable
import hous.release.android.util.BuildUtils
import java.io.Serializable

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? {
    return if (BuildUtils.isBundleValuesSafeToCast()) {
        getParcelableExtra(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getParcelableExtra(key) as? T
    }
}

inline fun <reified T : Serializable> Intent.serializable(key: String): T? {
    return if (BuildUtils.isBundleValuesSafeToCast()) {
        getSerializableExtra(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getSerializableExtra(key) as? T
    }
}
