package com.websarva.wings.android.flat.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.databinding.ItemHeaderBinding
import com.websarva.wings.android.flat.databinding.ItemMutualBinding
import com.websarva.wings.android.flat.databinding.ItemOneSideBinding
import com.websarva.wings.android.flat.viewmodel.ListItem

class FriendListAdapter(initialItem: List<ListItem>) :
    RecyclerView.Adapter<FriendListAdapter.BindingViewHolder>() {
    private val contents: MutableList<ListItem> = ArrayList(initialItem)

    companion object {
        private const val VIEW_TYPE_HEADER = 1
        private const val VIEW_TYPE_MUTUAL = 2
        private const val VIEW_TYPE_ONE_SIDE = 3
    }

    override fun getItemCount() = contents.size

    override fun getItemViewType(position: Int): Int {
        return when (contents[position]) {
            is ListItem.HeaderItem -> VIEW_TYPE_HEADER
            is ListItem.MutualItem -> VIEW_TYPE_MUTUAL
            is ListItem.OneSideItem -> VIEW_TYPE_ONE_SIDE
        }
    }

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BindingViewHolder(
            LayoutInflater.from(parent.context).inflate(getLayoutRes(viewType), parent, false)
        )

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        when (val item = contents[position]) {
            is ListItem.HeaderItem -> (holder.binding as ItemHeaderBinding).content = item
            is ListItem.MutualItem -> (holder.binding as ItemMutualBinding).content = item
            is ListItem.OneSideItem -> (holder.binding as ItemOneSideBinding).content = item
        }
    }
    //TODO::contentsを外部から制御するためのメソッドを書く
}