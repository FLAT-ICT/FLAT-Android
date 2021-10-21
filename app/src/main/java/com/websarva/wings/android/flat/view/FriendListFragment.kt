package com.websarva.wings.android.flat.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.databinding.FragmentFriendListBinding
import com.websarva.wings.android.flat.viewmodel.FriendListViewModel
import com.websarva.wings.android.flat.viewmodel.ListItem

class FriendListFragment : Fragment() {

    private val viewModel: FriendListViewModel by activityViewModels()
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

        binding.fabAddFriend.setOnClickListener {
            val action =
                FriendListFragmentDirections
                    .actionFriendListFragmentToAddFriendFragment()
            view.findNavController().navigate(action)
        }

        val list = ArrayList<ListItem>()
        val adapter = FriendListAdapter(childFragmentManager, viewModel, list)
        binding.apply {
            rvFriendList.adapter = adapter
            rvFriendList.layoutManager = LinearLayoutManager(context)
        }

        viewModel.operationUnapprovedFriends.observe(viewLifecycleOwner, {
            when(it[0]) {
                0 -> {
                    Log.d("AcceptCode", "Code=${it[1]}")
                    //TODO::Call getFriends
                }
                1 -> {
                    Log.d("RejectCode", "Code=${it[1]}, listPosition=${it[2]}")
                    //TODO::only call getFriends
                    if (it[1] == 200 && adapter.itemCount != 0) {
                        adapter.deleteItem(it[2])
                    }
                }
            }
            //TODO::if operate only calling getFriends, delete this
            if (adapter.itemCount > 1 && adapter.getItemViewType(0) == 1 && adapter.getItemViewType(1) == 1) {
                adapter.deleteItem(0)
            }
        })

        viewModel.getFriendsCode.observe(viewLifecycleOwner, {
            Log.d("getFriendCode", "$it")
            when (it) {
                200 -> binding.tvFriendListError.visibility = View.GONE
                else -> binding.tvFriendListError.visibility = View.VISIBLE
            }
        })

        viewModel.friendsCount.observe(viewLifecycleOwner, {
            Log.d("friends", "${viewModel.friendsCount.value}")
            if (it["oneSideCount"] == 0 && it["mutualCount"] == 0) {
                binding.tvNoHaveFriend.visibility = View.VISIBLE
            }
            if (it["oneSideCount"]!! > 0) {
                adapter.addItem(ListItem.HeaderItem(getString(R.string.unapproved_friends)))
                adapter.addItemList(viewModel.friends.value!!.one_side!!)
                binding.tvNoHaveFriend.visibility = View.GONE
            }
            if (it["mutualCount"]!! > 0) {
                adapter.addItem(ListItem.HeaderItem(getString(R.string.friends_list)))
                adapter.addItemList(viewModel.friends.value!!.mutual!!)
                binding.tvNoHaveFriend.visibility = View.GONE
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}