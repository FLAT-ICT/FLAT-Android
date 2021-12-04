package com.websarva.wings.android.flat.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.databinding.ItemUserSettingBinding
import com.websarva.wings.android.flat.model.UserSettingItem
import com.websarva.wings.android.flat.viewmodel.UserSettingViewModel

private object SettingListDiffCallback : DiffUtil.ItemCallback<UserSettingItem>() {
    override fun areItemsTheSame(oldItem: UserSettingItem, newItem: UserSettingItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserSettingItem, newItem: UserSettingItem): Boolean {
        return oldItem == newItem
    }
}

class UserSettingAdapter(
    private val viewLifecycleOwner: LifecycleOwner,
    private val viewModel: UserSettingViewModel
) : ListAdapter<UserSettingItem, UserSettingAdapter.UserSettingViewHolder>(SettingListDiffCallback) {

    class UserSettingViewHolder(private val binding: ItemUserSettingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserSettingItem, viewLifecycleOwner: LifecycleOwner, viewModel: UserSettingViewModel) {
            binding.run {
                lifecycleOwner = viewLifecycleOwner
                content = item
                this.viewModel = viewModel

                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserSettingViewHolder {
        return UserSettingViewHolder(
            ItemUserSettingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserSettingViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, viewLifecycleOwner, viewModel)
        holder.itemView.findViewById<TextView>(R.id.setting_item).apply {
            viewModel.apply {
                text = item.settingText
                setTextColor(context.getColor(setColor(item)))
            }
        }
    }
}