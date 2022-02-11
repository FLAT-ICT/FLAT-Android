package com.websarva.wings.android.flat.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.api.ResponseData
import com.websarva.wings.android.flat.databinding.ItemMutualBinding
import com.websarva.wings.android.flat.viewmodel.FriendListViewModel

private object FriendsDiffCallback : DiffUtil.ItemCallback<ResponseData.Mutual>() {
    override fun areItemsTheSame(
        oldItem: ResponseData.Mutual,
        newItem: ResponseData.Mutual
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ResponseData.Mutual,
        newItem: ResponseData.Mutual
    ): Boolean {
        return oldItem == newItem
    }
}

class FriendsAdapter(
    private val viewLifeCycleOwner: LifecycleOwner,
    private val viewModel: FriendListViewModel
) : ListAdapter<ResponseData.Mutual, FriendsAdapter.FriendsViewHolder>(FriendsDiffCallback) {

    class FriendsViewHolder(private val binding: ItemMutualBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ResponseData.Mutual,
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
        val ivFriendIcon = holder.itemView.findViewById<ImageView>(R.id.iv_mutual_icon)
        val urlString = getItem(position).icon_path
        Glide.with(holder.itemView.context).load(urlString).placeholder(R.drawable.default_user_icon).error(R.drawable.default_user_icon).into(ivFriendIcon)
        //TODO: アイテムクリック時の処理関数をviewModelから呼び出す
    }
}