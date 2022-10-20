package hous.release.android.presentation.our_rules.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import hous.release.android.databinding.ItemOurRulesGeneralRuleBinding
import hous.release.android.util.ItemDiffCallback
import hous.release.domain.entity.response.OurRule

class OurRulesAddAdapter : ListAdapter<OurRule, OurRulesAdapter.GeneralViewHolder>(
    itemDiffCallback
) {
    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OurRulesAdapter.GeneralViewHolder {
        if (!::inflater.isInitialized) inflater = LayoutInflater.from(parent.context)
        return OurRulesAdapter.GeneralViewHolder(
            ItemOurRulesGeneralRuleBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OurRulesAdapter.GeneralViewHolder, position: Int) {
        val data = currentList[position]
        holder.onBind(data)
    }

    companion object {
        private val itemDiffCallback = ItemDiffCallback<OurRule>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
