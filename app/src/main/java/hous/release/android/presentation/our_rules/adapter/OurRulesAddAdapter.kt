package hous.release.android.presentation.our_rules.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.databinding.ItemOurRulesGeneralRuleBinding
import hous.release.android.util.ItemDiffCallback
import hous.release.domain.entity.rule.OurRule

class OurRulesAddAdapter(private val hideKeyBoard: () -> Unit) :
    ListAdapter<OurRule, OurRulesAddAdapter.AddRuleViewHolder>(
        itemDiffCallback
    ) {
    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddRuleViewHolder {
        if (!::inflater.isInitialized) inflater = LayoutInflater.from(parent.context)
        return AddRuleViewHolder(
            hideKeyBoard,
            ItemOurRulesGeneralRuleBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AddRuleViewHolder, position: Int) {
        val data = currentList[position]
        holder.onBind(data)
    }

    class AddRuleViewHolder(
        private val hideKeyBoard: () -> Unit,
        private val binding: ItemOurRulesGeneralRuleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: OurRule) {
            binding.data = data
            binding.clRuleItem.setOnClickListener {
                hideKeyBoard()
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
