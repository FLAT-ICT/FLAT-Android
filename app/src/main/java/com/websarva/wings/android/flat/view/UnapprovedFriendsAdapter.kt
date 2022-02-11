package com.websarva.wings.android.flat.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
                user = item
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
        holder.bind(getItem(position), viewLifecycleOwner, viewModel)
        holder.itemView.findViewById<ImageButton>(R.id.ib_reject).setOnClickListener {
            RejectDialogFragment(item.id).show(fragmentManager, "dialog")
        }
        val ivFriendIcon = holder.itemView.findViewById<ImageView>(R.id.iv_one_side_icon)
        val urlString = item.icon_path
        Glide.with(holder.itemView.context).load(urlString).placeholder(R.drawable.default_user_icon).error(R.drawable.default_user_icon).into(ivFriendIcon)
    }
}