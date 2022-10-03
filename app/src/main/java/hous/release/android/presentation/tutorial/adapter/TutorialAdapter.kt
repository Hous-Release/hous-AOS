package hous.release.android.presentation.tutorial.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.databinding.ItemTutorialBinding
import hous.release.domain.entity.TutorialEntity

class TutorialAdapter :
    ListAdapter<TutorialEntity, TutorialAdapter.TutorialViewHolder>(tutorialDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorialViewHolder {
        val binding =
            ItemTutorialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TutorialViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TutorialViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class TutorialViewHolder(
        val binding: ItemTutorialBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: TutorialEntity) {
            binding.data = data
            absoluteAdapterPosition
        }
    }

    companion object {
        private val tutorialDiffUtil = object : DiffUtil.ItemCallback<TutorialEntity>() {
            override fun areItemsTheSame(
                oldItem: TutorialEntity,
                newItem: TutorialEntity
            ): Boolean =
                oldItem.head == newItem.head

            override fun areContentsTheSame(
                oldItem: TutorialEntity,
                newItem: TutorialEntity
            ): Boolean =
                oldItem == newItem
        }
    }
}