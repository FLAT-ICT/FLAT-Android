package com.websarva.wings.android.flat.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.databinding.FragmentFriendListBinding
import com.websarva.wings.android.flat.other.ListScrollable
import com.websarva.wings.android.flat.other.MyOnTabSelectedListener
import com.websarva.wings.android.flat.viewmodel.FriendListViewModel

class FriendListFragment : Fragment() {

    private val viewModel: FriendListViewModel by viewModels()
    private var _binding: FragmentFriendListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swipeAdapter = SwipeAdapter(TAB_TITLES.count(), this)
        binding.pager.adapter = swipeAdapter

        binding.tabLayout.addOnTabSelectedListener(object : MyOnTabSelectedListener() {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                super.onTabReselected(tab)

                val tabPosition = tab?.position
                if (tabPosition != null) {
                    val fragment = swipeAdapter.fragments.getOrNull(tabPosition)
                    (fragment as? ListScrollable)?.scrollToTop()
                }
            }
        })

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.setText(TAB_TITLES[position])
        }.attach()

        binding.fabAddFriend.setOnClickListener {
            val action =
                FriendListFragmentDirections
                    .actionFriendListFragmentToAddFriendFragment()
            view.findNavController().navigate(action)
        }


        viewModel.postRejectFriendCode.observe(viewLifecycleOwner, {
            Log.d("madakoko", "${viewModel.postApproveFriendCode}")
            when (it) {
                200 -> {
                    Log.d("kokomadeiketeru", "${viewModel.postApproveFriendCode}")
                }
                else -> {
                    //TODO: 拒否失敗時
                }
            }
        })


        viewModel.getFriendsCode.observe(viewLifecycleOwner, {
            Log.d("getFriendCode", "$it")
            when (it) {
                200 -> binding.tvFriendListError.visibility = View.GONE
//                else -> binding.tvFriendListError.visibility = View.VISIBLE
            }
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("destroy", "FriendListFragment")
        _binding = null
    }

    companion object {
        private val TAB_TITLES = arrayOf(
            R.string.friends_list,
            R.string.unapproved_friends
        )
    }
}