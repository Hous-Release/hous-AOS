package hous.release.android.util

import timber.log.Timber

class HousDeBugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        return "${element.fileName} : ${element.lineNumber} - ${element.methodName}"
    }
}
