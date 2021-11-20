package com.websarva.wings.android.flat.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.api.ResponseData
import com.websarva.wings.android.flat.databinding.ItemOneSideBinding
import com.websarva.wings.android.flat.viewmodel.FriendListViewModel

private object UnapprovedFriendsDiffCallback : DiffUtil.ItemCallback<ResponseData.OneSide>() {
    override fun areItemsTheSame(
        oldItem: ResponseData.OneSide,
        newItem: ResponseData.OneSide
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ResponseData.OneSide,
        newItem: ResponseData.OneSide
    ): Boolean {
        return oldItem == newItem
    }
}

class UnapprovedFriendsAdapter(
    private val fragmentManager: FragmentManager,
    private val viewLifecycleOwner: LifecycleOwner,
    private val viewModel: FriendListViewModel
) : ListAdapter<ResponseData.OneSide, UnapprovedFriendsAdapter.UnapprovedFriendsViewHolder>(
    UnapprovedFriendsDiffCallback
) {

    class UnapprovedFriendsViewHolder(private val binding: ItemOneSideBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ResponseData.OneSide,
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