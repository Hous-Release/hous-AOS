package hous.release.android.presentation.our_rules.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.databinding.ItemOurRulesDeleteItemBinding
import hous.release.android.util.ItemDiffCallback
import hous.release.domain.entity.response.OurRule

class OurRulesDeleteAdapter(private val updateDeleteRules: (id: Int) -> Unit) :
    ListAdapter<OurRule, OurRulesDeleteAdapter.DeleteOurRuleViewHolder>(itemDiffCallback) {
    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeleteOurRuleViewHolder {
        if (!::inflater.isInitialized) inflater = LayoutInflater.from(parent.context)
        return DeleteOurRuleViewHolder(
            ItemOurRulesDeleteItemBinding.inflate(
                inflater,
                parent,
                false
            ),
            updateDeleteRules
        )
    }

    override fun onBindViewHolder(holder: DeleteOurRuleViewHolder, position: Int) {
        val data = currentList[position]
        holder.onBind(data)
    }

    class DeleteOurRuleViewHolder(
        private val binding: ItemOurRulesDeleteItemBinding,
        private val updateDeleteRules: (id: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: OurRule) {
            binding.pos = absoluteAdapterPosition
            binding.data = data
            with(binding) {
                clDeleteRule.setOnClickListener {
                    updateDeleteRules(data.id)
                    checkboxDeleteRule.isSelected = !checkboxDeleteRule.isSelected
                }
                checkboxDeleteRule.setOnClickListener {
                    updateDeleteRules(data.id)
                    checkboxDeleteRule.isSelected = !checkboxDeleteRule.isSelected
                }
            }
        }
    }

    companion object {
        private val itemDiffCallback = ItemDiffCallback<OurRule>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
