package hous.release.android.util

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecorationUtil(
    private val context: Context,
    dp: Int,
    private val position: Int
) :
    RecyclerView.ItemDecoration() {
    private val margin = getPixelValue(dp)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val viewPosition = parent.getChildAdapterPosition(view)
        if (viewPosition == position) {
            outRect.top = margin
        }
    }

    private fun getPixelValue(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }
}
