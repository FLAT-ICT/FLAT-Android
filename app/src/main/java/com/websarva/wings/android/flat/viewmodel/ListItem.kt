package com.websarva.wings.android.flat.viewmodel

sealed class ListItem {
    class HeaderItem(val label: String) : ListItem()
    class MutualItem(
        val name: String,
        val status: String,
        val icon_path: String,
        val beacon: String
    ) : ListItem()

    class OneSideItem(val name: String, val icon_path: String) : ListItem()
}
