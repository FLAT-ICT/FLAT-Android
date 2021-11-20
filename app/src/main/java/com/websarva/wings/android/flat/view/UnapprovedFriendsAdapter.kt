package com.websarva.wings.android.flat.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.databinding.ItemOneSideBinding
import com.websarva.wings.android.flat.viewmodel.FriendListViewModel
import com.websarva.wings.android.flat.viewmodel.ListItem

private object UnapprovedFriendsDiffCallback : DiffUtil.ItemCallback<ListItem.OneSideItem>() {
    override fun areItemsTheSame(
        oldItem: ListItem.OneSideItem,
        newItem: ListItem.OneSideItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ListItem.OneSideItem,
        newItem: ListItem.OneSideItem
    ): Boolean {
        return oldItem.name == newItem.name && oldItem.icon_path == newItem.icon_path && oldItem.status == newItem.status && oldItem.spot == newItem.spot
    }
}

class UnapprovedFriendsAdapter(
    private val fragmentManager: FragmentManager,
    private val viewLifecycleOwner: LifecycleOwner,
    private val viewModel: FriendListViewModel
) : ListAdapter<ListItem.OneSideItem, UnapprovedFriendsAdapter.UnapprovedFriendsViewHolder>(
    UnapprovedFriendsDiffCallback
) {

    class UnapprovedFriendsViewHolder(private val binding: ItemOneSideBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ListItem.OneSideItem,
            viewLifecycleOwner: LifecycleOwner,
            viewModel: FriendListViewModel
        ) {
            binding.run {
                lifecycleOwner = viewLifecycleOwner
                content = item
                this.viewModel = viewModel
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnapprovedFriendsViewHolder {
        return UnapprovedFriendsViewHolder(
            ItemOneSideBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UnapprovedFriendsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, viewLifecycleOwner, viewModel)
        holder.itemView.findViewById<ImageButton>(R.id.ib_accept).setOnClickListener {
            viewModel.postApproveFriend(item.id)
        }
        holder.itemView.findViewById<ImageButton>(R.id.ib_reject).setOnClickListener {
            RejectDialogFragment(item.id).show(fragmentManager, "dialog")
        }
    }
}