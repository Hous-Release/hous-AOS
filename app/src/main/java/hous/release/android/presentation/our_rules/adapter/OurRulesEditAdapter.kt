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
import timber.log.Timber
import java.util.*

class OurRulesEditAdapter(
    private val updateEditRuleList: (List<OurRule>) -> Unit,
    private val initRuleList: (List<OurRule>) -> Unit
) :
    ListAdapter<OurRule, OurRulesEditAdapter.EditRuleViewHolder>(
        itemDiffCallback
    ),
    ItemTouchHelperCallback.OnItemMoveListener {
    private lateinit var inflater: LayoutInflater
    private lateinit var dragListener: OnStartDragListener
    private lateinit var mCurrentList: MutableList<OurRule>

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
        Timber.e(" ")
        if (viewHolder is EditRuleViewHolder) {
            viewHolder.setBackGroundWhenDrag()
            Timber.e(currentList.toList().toString())
            mCurrentList = currentList.toMutableList().also { curList ->
                val selectedItemIdx = viewHolder.absoluteAdapterPosition
                val ruleType = curList[selectedItemIdx].ruleType
                curList[selectedItemIdx] = curList[selectedItemIdx].toNoDividerRule(ruleType)
            }
        }
    }

    override fun onItemMoved(fromPos: Int, toPos: Int) {
        Timber.e(" ")
        mCurrentList = currentList.toMutableList().also { editList ->
            Collections.swap(editList, fromPos, toPos)
        }.also { curList ->
            initRuleList(curList)
        }
        submitList(mCurrentList)
    }

    override fun onItemCleared(viewHolder: RecyclerView.ViewHolder) {
        Timber.e(" ")
        mCurrentList = mCurrentList.also { curList ->
            val selectedItemIdx = viewHolder.absoluteAdapterPosition
            val ruleType = curList[selectedItemIdx].ruleType
            curList[selectedItemIdx] = curList[selectedItemIdx].toDividerRule(ruleType)
        }
        updateEditRuleList(mCurrentList)
        return
    }

    class EditRuleViewHolder(
        private val dragListener: OnStartDragListener,
        private val binding: ItemOurRulesEditItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var data: OurRule
        fun setBackGroundWhenDrag() {
            binding.clRuleItem.setBackgroundResource(R.color.hous_white)
            binding.divider.visibility = View.INVISIBLE
        }

        @SuppressLint("ClickableViewAccessibility")
        fun onBind(data: OurRule) {
            this.data = data
            binding.data = data
            with(binding.clDragHandler) {
                visibility = View.VISIBLE
                binding.clDragHandler.setOnTouchListener { _: View, event: MotionEvent ->
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        dragListener.onStartDrag(this@EditRuleViewHolder)
                    }
                    return@setOnTouchListener false
                }
            }
            when (data.ruleType) {
                is OurRule.RuleType.RepresentativeRule -> {
                    val ruleType = data.ruleType as OurRule.RuleType.RepresentativeRule
                    if (!ruleType.hasDivider) {
                        binding.divider.visibility = View.INVISIBLE
                    } else {
                        binding.divider.visibility = View.VISIBLE
                    }
                    binding.clRuleItem.setBackgroundResource(R.color.hous_blue_l2)
                    binding.divider.setBackgroundResource(R.color.hous_blue_l1)
                }
                is OurRule.RuleType.GeneralRule -> {
                    binding.clRuleItem.setBackgroundResource(R.color.hous_white)
                    binding.divider.visibility = View.VISIBLE
                    binding.divider.setBackgroundResource(R.color.hous_g_2)
                }
            }
        }
    }

    interface OnStartDragListener {
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    }

    companion object {
        private val itemDiffCallback = ItemDiffCallback<OurRule>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
