package hous.release.android.presentation.hous.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.R
import hous.release.android.databinding.ItemHousHomiesBinding
import hous.release.android.util.ItemDiffCallback
import hous.release.domain.entity.HomyType
import hous.release.domain.entity.response.Homy

class HomiesAdapter : ListAdapter<Homy, HomiesAdapter.HomiesViewHolder>(homyDiffUtil) {
    class HomiesViewHolder(
        private val binding: ItemHousHomiesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(homy: Homy) {
            binding.homy = homy
            binding.executePendingBindings()
            initHomyProfileImg(HomyType.valueOf(homy.color))
            // TODO 추후 Homie 상세보기 화면 이동 호출 필요
        }

        private fun initHomyProfileImg(color: HomyType) {
            binding.ivHousProfile.setImageResource(
                when (color) {
                    HomyType.RED -> R.drawable.ic_profile_red
                    HomyType.YELLOW -> R.drawable.ic_profile_yellow
                    HomyType.BLUE -> R.drawable.ic_profile_blue
                    HomyType.PURPLE -> R.drawable.ic_profile_purple
                    HomyType.GREEN -> R.drawable.ic_profile_green
                    HomyType.GRAY -> R.drawable.ic_profile_gray
                }
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomiesViewHolder =
        HomiesViewHolder(
            ItemHousHomiesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
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
