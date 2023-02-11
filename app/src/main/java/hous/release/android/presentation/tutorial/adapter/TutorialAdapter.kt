package hous.release.android.presentation.tutorial.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.R
import hous.release.android.databinding.ItemTutorialBinding
import hous.release.android.util.ItemDiffCallback
import hous.release.domain.entity.TutorialEntity

class TutorialAdapter :
    ListAdapter<TutorialEntity, TutorialAdapter.TutorialViewHolder>(tutorialDiffCallback) {

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
            initTutorialImg(absoluteAdapterPosition)
        }

        private fun initTutorialImg(position: Int) {
            binding.ivTutorialImage.setImageResource(
                when (position) {
                    FIRST -> R.drawable.ic_tutorial_1
                    SECOND -> R.drawable.ic_tutorial_2
                    THIRD -> R.drawable.ic_tutorial_3
                    else -> R.drawable.ic_tutorial_4
                }
            )
        }
    }

    companion object {
        private val tutorialDiffCallback = ItemDiffCallback<TutorialEntity>(
            onItemsTheSame = { old, new -> old.head == new.head },
            onContentsTheSame = { old, new -> old == new }
        )
        private const val FIRST = 0
        private const val SECOND = 1
        private const val THIRD = 2
    }
}
