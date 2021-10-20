package com.websarva.wings.android.flat.viewmodel

sealed class ListItem {
    class HeaderItem(val label: String) : ListItem()
    class MutualItem(
        val id: String,
        val name: String,
        val status: Int,
        val beacon: String?,
        val icon_path: String
    ) : ListItem()

    class OneSideItem(
        val id: String,
        val name: String,
        val status: Int,
        val beacon: String?,
        val icon_path: String
    ) : ListItem()
}
