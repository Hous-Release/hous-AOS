package hous.release.android.presentation.out_room.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hous.release.android.databinding.ItemOutRoomMyToDoBinding
import hous.release.android.util.ItemDiffCallback
import hous.release.domain.entity.response.MyToDo

class MyToDoAdapter : ListAdapter<MyToDo, MyToDoAdapter.MyToDoViewHolder>(myToDoDiffUtil) {
    class MyToDoViewHolder(
        private val binding: ItemOutRoomMyToDoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(myToDo: MyToDo) {
            binding.myToDo = myToDo
            binding.executePendingBindings()
            // TODO 추후 Homie 상세보기 화면 이동 호출 필요
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyToDoViewHolder =
        MyToDoViewHolder(
            ItemOutRoomMyToDoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MyToDoAdapter.MyToDoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val myToDoDiffUtil =
            ItemDiffCallback<MyToDo>(
                onItemsTheSame = { old, new -> old.id == new.id },
                onContentsTheSame = { old, new -> old == new }
            )
    }
}
