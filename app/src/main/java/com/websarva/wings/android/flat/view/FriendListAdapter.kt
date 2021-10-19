package com.websarva.wings.android.flat.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
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

    // Headerを含めた要素数を返す
    override fun getItemCount() = contents.size

    // Itemの型からviewTypeを返す
    override fun getItemViewType(position: Int): Int {
        return when (contents[position]) {
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
        when (val item = contents[position]) {
            is ListItem.HeaderItem -> (holder.binding as ItemHeaderBinding).content = item
            is ListItem.MutualItem -> (holder.binding as ItemMutualBinding).content = item
            is ListItem.OneSideItem -> {
                (holder.binding as ItemOneSideBinding).content = item
                holder.binding.ibAccept.setOnClickListener {
                    //TODO::accept時の処理
                    val p = holder.bindingAdapterPosition
                    Log.d("button", "$p")
                }
                holder.binding.ibReject.setOnClickListener {
                    //TODO::reject時の処理
                    val i = item.id
                    val p = item.name
                    Log.d("button", "${p}, $i")
                }
            }
        }
    }

    //TODO::contentsを外部から制御するためのメソッドを書く
    fun addItem(item: ListItem) {
        val addIndex = contents.size
        contents.add(item)
        notifyItemInserted(addIndex)
    }

    fun addItemList(items: List<ListItem>) {
        for (item in items) {
            val addIndex = contents.size
            contents.add(item)
            notifyItemInserted(addIndex)
        }
    }

    fun deleteItem(item: ListItem) {
        contents.remove(item)
    }
}