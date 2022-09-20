package hous.release.android.presentation.our_rules.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.databinding.ItemOurRulesGeneralRuleBinding
import hous.release.android.databinding.ItemOurRulesRepresentativeRuleBottomBinding
import hous.release.android.databinding.ItemOurRulesRepresentativeRuleMiddleBinding
import hous.release.android.databinding.ItemOurRulesRepresentativeRuleTopBinding
import hous.release.android.presentation.our_rules.type.RuleItemViewType
import hous.release.domain.entity.OurRuleEntity

class OurRulesAdapter : ListAdapter<OurRuleEntity, RecyclerView.ViewHolder>(ourRulesDiffUtilCallback) {

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> RuleItemViewType.REPRESENTATVIE_TOP.id
            1 -> RuleItemViewType.REPRESENTATVIE_MIDDLE.id
            2 -> RuleItemViewType.REPRESENTATVIE_BOTTOM.id
            else -> RuleItemViewType.GENERAL.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            RuleItemViewType.REPRESENTATVIE_TOP.id -> RepresentationTopViewHolder(
                ItemOurRulesRepresentativeRuleTopBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            RuleItemViewType.REPRESENTATVIE_MIDDLE.id -> RepresentationMiddleViewHolder(
                ItemOurRulesRepresentativeRuleMiddleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            RuleItemViewType.REPRESENTATVIE_BOTTOM.id -> RepresentationBottomViewHolder(
                ItemOurRulesRepresentativeRuleBottomBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            RuleItemViewType.GENERAL.id -> GeneralViewHolder(
                ItemOurRulesGeneralRuleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("viewType : $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = currentList[position]
        when (holder) {
            is RepresentationTopViewHolder -> holder.onBind(data)
            is RepresentationMiddleViewHolder -> holder.onBind(data)
            is RepresentationBottomViewHolder -> holder.onBind(data)
            is GeneralViewHolder -> holder.onBind(data)
            else -> throw IllegalArgumentException("holder : $holder")
        }
    }

    class RepresentationTopViewHolder(
        private val binding: ItemOurRulesRepresentativeRuleTopBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: OurRuleEntity) {
            binding.data = data
        }
    }

    class RepresentationMiddleViewHolder(
        private val binding: ItemOurRulesRepresentativeRuleMiddleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: OurRuleEntity) {
            binding.data = data
        }
    }

    class RepresentationBottomViewHolder(
        private val binding: ItemOurRulesRepresentativeRuleBottomBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: OurRuleEntity) {
            binding.data = data
        }
    }

    class GeneralViewHolder(
        private val binding: ItemOurRulesGeneralRuleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: OurRuleEntity) {
            binding.data = data
        }
    }

    companion object {
        private val ourRulesDiffUtilCallback =
            object : DiffUtil.ItemCallback<OurRuleEntity>() {
                override fun areItemsTheSame(
                    oldItem: OurRuleEntity,
                    newItem: OurRuleEntity
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: OurRuleEntity,
                    newItem: OurRuleEntity
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
