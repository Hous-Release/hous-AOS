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
import hous.release.domain.entity.response.OurRule
import timber.log.Timber

class OurRulesAdapter : ListAdapter<OurRule, RecyclerView.ViewHolder>(ourRulesDiffUtilCallback) {
    private lateinit var inflater: LayoutInflater

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> RuleItemViewType.REPRESENTATVIE_TOP.id
            1 -> RuleItemViewType.REPRESENTATVIE_MIDDLE.id
            2 -> RuleItemViewType.REPRESENTATVIE_BOTTOM.id
            else -> RuleItemViewType.GENERAL.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (!::inflater.isInitialized) inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            RuleItemViewType.REPRESENTATVIE_TOP.id -> {
                RepresentationTopViewHolder(
                    ItemOurRulesRepresentativeRuleTopBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            RuleItemViewType.REPRESENTATVIE_MIDDLE.id -> RepresentationMiddleViewHolder(
                ItemOurRulesRepresentativeRuleMiddleBinding.inflate(
                    inflater,
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
                    inflater,
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
            else -> Timber.e(IllegalArgumentException("holder : $holder"))
        }
    }

    class RepresentationTopViewHolder(
        private val binding: ItemOurRulesRepresentativeRuleTopBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: OurRule) {
            binding.data = data
        }
    }

    class RepresentationMiddleViewHolder(
        private val binding: ItemOurRulesRepresentativeRuleMiddleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: OurRule) {
            binding.data = data
        }
    }

    class RepresentationBottomViewHolder(
        private val binding: ItemOurRulesRepresentativeRuleBottomBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: OurRule) {
            binding.data = data
        }
    }

    class GeneralViewHolder(
        private val binding: ItemOurRulesGeneralRuleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: OurRule) {
            binding.data = data
        }
    }

    companion object {
        private val ourRulesDiffUtilCallback =
            object : DiffUtil.ItemCallback<OurRule>() {
                override fun areItemsTheSame(
                    oldItem: OurRule,
                    newItem: OurRule
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: OurRule,
                    newItem: OurRule
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
