package com.websarva.wings.android.flat.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.api.ResponseData
import com.websarva.wings.android.flat.databinding.ItemHeaderBinding
import com.websarva.wings.android.flat.databinding.ItemMutualBinding
import com.websarva.wings.android.flat.databinding.ItemOneSideBinding
import com.websarva.wings.android.flat.viewmodel.FriendListViewModel
import com.websarva.wings.android.flat.viewmodel.ListItem

private object FriendDiffCallback: DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }
}

class FriendListAdapter(private val childFragmentManager: FragmentManager,
                        private val viewModel: FriendListViewModel,
): ListAdapter<ListItem, FriendListAdapter.BindingViewHolder>(FriendDiffCallback) {

    companion object {
        private const val VIEW_TYPE_HEADER = 1
        private const val VIEW_TYPE_MUTUAL = 2
        private const val VIEW_TYPE_ONE_SIDE = 3
    }

    // Headerを含めた要素数を返す
    override fun getItemCount() = currentList.size

    // Itemの型からviewTypeを返す
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ListItem.HeaderItem -> VIEW_TYPE_HEADER
            is ListItem.MutualItem -> VIEW_TYPE_MUTUAL
            is ListItem.OneSideItem -> VIEW_TYPE_ONE_SIDE
        }
    }

    // viewTypeをもとに使用するレイアウトIDを取得する
    private fun getLayoutRes(viewType: Int) =
        when (viewType) {
            VIEW_TYPE_HEADER -> R.layout.item_header
            VIEW_TYPE_MUTUAL -> R.layout.item_mutual
            VIEW_TYPE_ONE_SIDE -> R.layout.item_one_side
            else -> throw IllegalArgumentException("Unknown viewType $viewType")
        }

    class BindingViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val binding: ViewDataBinding = DataBindingUtil.bind(v)!!
    }


    // Viewをinflate
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(getLayoutRes(viewType), parent, false)
        val holder = BindingViewHolder(itemView)
        holder.itemView.setOnClickListener {
            //TODO::詳細画面表示の処理
            if (viewType != 1) {
                val position = holder.bindingAdapterPosition
                Log.d("itemView", "$position")
            }
        }
        return holder
    }

    // Itemを適切な型にキャストしてviewに値をセットする
    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is ListItem.HeaderItem -> (holder.binding as ItemHeaderBinding).content = item
            is ListItem.MutualItem -> (holder.binding as ItemMutualBinding).content = item
            is ListItem.OneSideItem -> {
                (holder.binding as ItemOneSideBinding).content = item
                //accept時の処理
                holder.binding.ibAccept.setOnClickListener {
                    viewModel.postAcceptFriend(item.id)
                }
                //reject時の処理
                holder.binding.ibReject.setOnClickListener {
                    RejectDialogFragment(item.id).show(childFragmentManager, "dialog")
                }
            }
        }
    }
}