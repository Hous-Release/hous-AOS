package hous.release.android.util.extension

import android.os.Bundle
import android.os.Parcelable
import hous.release.android.util.BuildUtils
import java.io.Serializable

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? {
    return if (BuildUtils.isBundleValuesSafeToCast()) {
        getParcelable(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getParcelable(key) as? T
    }
}

inline fun <reified T : Serializable> Bundle.serializable(key: String): T? {
    return if (BuildUtils.isBundleValuesSafeToCast()) {
        getSerializable(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getSerializable(key) as? T
    }
}
