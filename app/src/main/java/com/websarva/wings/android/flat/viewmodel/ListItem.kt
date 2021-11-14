package com.websarva.wings.android.flat.viewmodel

sealed class ListItem {
    class HeaderItem(val label: String) : ListItem()
    class MutualItem(
        val id: Int,
        val name: String,
        val status: Int,
        val spot: String,
        val icon_path: String
    ) : ListItem()

    class OneSideItem(
        val id: Int,
        val name: String,
        val status: Int,
        val spot: String,
        val icon_path: String
    ) : ListItem()
}
