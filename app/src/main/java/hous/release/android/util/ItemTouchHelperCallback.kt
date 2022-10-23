package hous.release.android.util

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemTouchHelperCallback(private val itemMoveListener: OnItemMoveListener) :
    ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(dragFlags, 0)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        itemMoveListener.onItemMoved(
            viewHolder.absoluteAdapterPosition,
            target.absoluteAdapterPosition
        )
        return true
    }

    override fun isLongPressDragEnabled(): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) = Unit

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if ((actionState == ItemTouchHelper.ACTION_STATE_DRAG) && viewHolder != null) {
            itemMoveListener.onItemSelected(viewHolder)
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        itemMoveListener.onItemCleared(viewHolder)
    }

    interface OnItemMoveListener {
        fun onItemMoved(fromPos: Int, toPos: Int)
        fun onItemSelected(viewHolder: RecyclerView.ViewHolder)
        fun onItemCleared(viewHolder: RecyclerView.ViewHolder)
    }
}
