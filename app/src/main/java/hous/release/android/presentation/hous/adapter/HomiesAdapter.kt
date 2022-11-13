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
import timber.log.Timber

class HomiesAdapter(
    private val onClickHomie: (Homy) -> Unit
) : ListAdapter<Homy, HomiesAdapter.HomiesViewHolder>(homyDiffUtil) {
    class HomiesViewHolder(
        private val binding: ItemHousHomiesBinding,
        private val onClickHomie: (Homy) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(homy: Homy) {
            binding.homy = homy
            binding.executePendingBindings()
            initHomyProfileImg(HomyType.valueOf(homy.color))
            binding.clHousProfile.setOnClickListener {
                onClickHomie(homy)
                Timber.e("${homy.homieId}")
            }
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
