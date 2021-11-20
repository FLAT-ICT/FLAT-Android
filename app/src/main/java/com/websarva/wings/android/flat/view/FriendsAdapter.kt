package com.websarva.wings.android.flat.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.websarva.wings.android.flat.databinding.ItemMutualBinding
import com.websarva.wings.android.flat.viewmodel.FriendListViewModel
import com.websarva.wings.android.flat.viewmodel.ListItem

private object FriendsDiffCallback : DiffUtil.ItemCallback<ListItem.MutualItem>() {
    override fun areItemsTheSame(
        oldItem: ListItem.MutualItem,
        newItem: ListItem.MutualItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ListItem.MutualItem,
        newItem: ListItem.MutualItem
    ): Boolean {
        return oldItem.name == newItem.name && oldItem.icon_path == newItem.icon_path && oldItem.status == newItem.status && oldItem.spot == newItem.spot
    }
}

class FriendsAdapter(
    private val viewLifeCycleOwner: LifecycleOwner,
    private val viewModel: FriendListViewModel
) : ListAdapter<ListItem.MutualItem, FriendsAdapter.FriendsViewHolder>(FriendsDiffCallback) {

    class FriendsViewHolder(private val binding: ItemMutualBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ListItem.MutualItem,
            viewLifeCycleOwner: LifecycleOwner,
            viewModel: FriendListViewModel
        ) {
            binding.run {
                lifecycleOwner = viewLifeCycleOwner
                content = item
                this.viewModel = viewModel

                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        return FriendsViewHolder(
            ItemMutualBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        holder.bind(getItem(position), viewLifeCycleOwner, viewModel)
        //TODO: アイテムクリック時の処理関数をviewModelから呼び出す
    }
}