package com.websarva.wings.android.flat.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.api.ResponseData
import com.websarva.wings.android.flat.databinding.ItemSearchedUsersBinding
import com.websarva.wings.android.flat.viewmodel.AddFriendViewModel

private object DiffCallback: DiffUtil.ItemCallback<ResponseData.ResponseSearchUser>() {
    override fun areItemsTheSame(
        oldItem: ResponseData.ResponseSearchUser,
        newItem: ResponseData.ResponseSearchUser
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ResponseData.ResponseSearchUser,
        newItem: ResponseData.ResponseSearchUser
    ): Boolean {
        return oldItem == newItem
    }
}

class AddFriendAdapter(
    private val viewLifeCycleOwner: LifecycleOwner,
    private val viewModel: AddFriendViewModel
): ListAdapter<ResponseData.ResponseSearchUser, AddFriendAdapter.AddFriendViewHolder>(DiffCallback) {

    class AddFriendViewHolder(private val binding: ItemSearchedUsersBinding):
    RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseData.ResponseSearchUser, viewLifeCycleOwner: LifecycleOwner, viewModel: AddFriendViewModel) {
            binding.run {
                lifecycleOwner = viewLifeCycleOwner
                user = item
                this.viewModel = viewModel

                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddFriendViewHolder {
        return AddFriendViewHolder(ItemSearchedUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AddFriendViewHolder, position: Int) {
        holder.bind(getItem(position), viewLifeCycleOwner, viewModel)
        holder.itemView.findViewById<Button>(R.id.bt_friend_request).apply {
            viewModel.apply {
                text = context.getString(setButtonText(getItem(position)))
                setTextColor(context.getColor(setButtonTextColor(getItem(position))))
                setBackgroundColor(context.getColor(setButtonBackgroundColor(getItem(position))))
            }
        }

    }
}