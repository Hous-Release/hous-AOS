package hous.release.android.presentation.hous.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.databinding.ItemHousHomiesBinding
import hous.release.android.util.ItemDiffCallback
import hous.release.android.util.extension.setOnSingleClickListener
import hous.release.domain.entity.response.Homy

class HomiesAdapter(
    private val onClickHomie: (Homy, Int) -> Unit
) : ListAdapter<Homy, HomiesAdapter.HomiesViewHolder>(homyDiffUtil) {
    class HomiesViewHolder(
        private val binding: ItemHousHomiesBinding,
        private val onClickHomie: (Homy, Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(homy: Homy) {
            binding.homy = homy
            binding.executePendingBindings()
            binding.clHousProfile.setOnSingleClickListener {
                onClickHomie(homy, absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomiesViewHolder =
        HomiesViewHolder(
            ItemHousHomiesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClickHomie
        )

    override fun onBindViewHolder(holder: HomiesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val homyDiffUtil =
            ItemDiffCallback<Homy>(
                onItemsTheSame = { old, new -> old.homieId == new.homieId },
                onContentsTheSame = { old, new -> old == new }
            )
    }
}
