package com.websarva.wings.android.flat.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SwipeAdapter(
    private val tabsCount: Int,
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    private val _fragments = mutableListOf<Fragment>()

    val fragments: List<Fragment> get() = _fragments

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> FriendsFragment()
            1 -> UnapprovedFriendsFragment()
            else -> throw IllegalArgumentException("Unknown viewType $position")
        }
    }

    override fun getItemCount(): Int = tabsCount
}