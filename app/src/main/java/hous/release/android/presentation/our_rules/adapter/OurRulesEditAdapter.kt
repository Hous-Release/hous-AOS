package hous.release.android.presentation.our_rules.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.R
import hous.release.android.databinding.ItemOurRulesEditItemBinding
import hous.release.android.util.ItemDiffCallback
import hous.release.android.util.ItemTouchHelperCallback
import hous.release.domain.entity.response.OurRule
import java.util.Collections

class OurRulesEditAdapter(
    private val updateEditRuleList: (List<OurRule>) -> Unit
) :
    ListAdapter<OurRule, OurRulesEditAdapter.EditRuleViewHolder>(
        itemDiffCallback
    ),
    ItemTouchHelperCallback.OnItemMoveListener {
    private lateinit var inflater: LayoutInflater
    private lateinit var dragListener: OnStartDragListener

    fun startDrag(listener: OnStartDragListener) {
        this.dragListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EditRuleViewHolder {
        if (!::inflater.isInitialized) inflater = LayoutInflater.from(parent.context)
        return EditRuleViewHolder(
            dragListener,
            ItemOurRulesEditItemBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EditRuleViewHolder, position: Int) {
        val data = currentList[position]
        holder.onBind(data)
    }

    override fun onItemSelected(viewHolder: RecyclerView.ViewHolder) {
        (viewHolder as EditRuleViewHolder).changeItemBackGroundWhenSelected()
    }

    override fun onItemMoved(fromPos: Int, toPos: Int) {
        val editList = currentList.toMutableList().also { editList ->
            Collections.swap(editList, fromPos, toPos)
        }
        submitList(editList)
    }

    override fun onItemCleared(viewHolder: RecyclerView.ViewHolder) {
        (viewHolder as EditRuleViewHolder).initItemBackGround()
        updateEditRuleList(currentList)
        return
    }

    class EditRuleViewHolder(
        private val dragListener: OnStartDragListener,
        private val binding: ItemOurRulesEditItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun changeItemBackGroundWhenSelected() {
            binding.clRuleItem.setBackgroundResource(R.color.hous_white)
            binding.divider.visibility = View.INVISIBLE
        }

        fun initItemBackGround() {
            when (absoluteAdapterPosition) {
                in 0..1 -> {
                    binding.divider.visibility = View.VISIBLE
                    binding.clRuleItem.setBackgroundResource(R.color.hous_blue_l2)
                    binding.divider.setBackgroundResource(R.color.hous_blue_l1)
                }
                2 -> {
                    binding.divider.visibility = View.INVISIBLE
                    binding.clRuleItem.setBackgroundResource(R.color.hous_blue_l2)
                }
                else -> {
                    binding.clRuleItem.setBackgroundResource(R.color.hous_white)
                    binding.divider.visibility = View.VISIBLE
                    binding.divider.setBackgroundResource(R.color.hous_g_2)
                }
            }
        }

        @SuppressLint("ClickableViewAccessibility")
        fun onBind(data: OurRule) {
            binding.edtRuleDescription.setText(data.name)
            binding.edtRuleDescription.hint = data.name

            with(binding.clDragHandler) {
                visibility = View.VISIBLE
                setOnTouchListener { _: View, event: MotionEvent ->
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        dragListener.onStartDrag(this@EditRuleViewHolder)
                    }
                    return@setOnTouchListener false
                }
            }
            initItemBackGround()
        }
    }

    interface OnStartDragListener {
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    }

    companion object {
        private val itemDiffCallback = ItemDiffCallback<OurRule>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new ->
                old == new
            }
        )
    }
}
