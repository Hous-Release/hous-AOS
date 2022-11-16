package hous.release.android.presentation.our_rules.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.R
import hous.release.android.databinding.ItemOurRulesEditItemBinding
import hous.release.android.util.ItemDiffCallback
import hous.release.android.util.ItemTouchHelperCallback
import hous.release.domain.entity.RuleType
import hous.release.domain.entity.response.OurRule
import java.util.*

class OurRulesEditAdapter(
    private val updateEditRuleList: (List<OurRule>) -> Unit,
    private val editRuleName: (place: Int, newRuleName: String) -> Unit,
    private val hideKeyBoard: () -> Unit
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
            hideKeyBoard,
            editRuleName,
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
        private val hideKeyBoard: () -> Unit,
        private val editRuleName: (place: Int, newRuleName: String) -> Unit,
        private val dragListener: OnStartDragListener,
        private val binding: ItemOurRulesEditItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun changeItemBackGroundWhenSelected() {
            hideKeyBoard()
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
            initRuleTextField(data)
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

        private fun initRuleTextField(data: OurRule) {
            binding.edtRuleDescription.setText(data.name)
            binding.edtRuleDescription.hint = data.name
            binding.edtRuleDescription.addTextChangedListener { newRuleName ->
                editRuleName(absoluteAdapterPosition, newRuleName.toString())
            }
            binding.edtRuleDescription.setOnFocusChangeListener { _, isFocus ->
                if (isFocus) {
                    if (data.ruleType == RuleType.GENERAL) {
                        return@setOnFocusChangeListener binding.clRuleItem.setBackgroundResource(
                            R.color.hous_g_1
                        )
                    }
                    binding.clRuleItem.setBackgroundResource(R.color.hous_blue_edit)
                } else {
                    if (data.ruleType == RuleType.GENERAL) {
                        return@setOnFocusChangeListener binding.clRuleItem.setBackgroundResource(
                            R.color.hous_white
                        )
                    }
                    binding.clRuleItem.setBackgroundResource(R.color.hous_blue_l2)
                }
            }
            binding.clRuleItem.setOnClickListener {
                hideKeyBoard()
            }
        }
    }

    interface OnStartDragListener {
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    }

    companion object {
        private val itemDiffCallback = ItemDiffCallback<OurRule>(
            onItemsTheSame = { old, new ->
                old.id == new.id
            },
            onContentsTheSame = { old, new ->
                old.ruleType == new.ruleType
            }
        )
    }
}
