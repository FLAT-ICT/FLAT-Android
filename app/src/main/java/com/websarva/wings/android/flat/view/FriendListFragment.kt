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
        val adapter = FriendListAdapter(childFragmentManager, viewModel)
        binding.apply {
            rvFriendList.adapter = adapter
            rvFriendList.layoutManager = LinearLayoutManager(context)
        }

        viewModel.operationUnapprovedFriends.observe(viewLifecycleOwner, {
            when(it[1]) {
                200 -> {
                    Log.d("Approve or Reject",
                        if (it[0] == 0) "ApproveSuccess" else "RejectSuccess"
                    )
                    viewModel.getFriends()
                }
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
            list.clear()
            if (it["oneSideCount"] == 0 && it["mutualCount"] == 0) {
                binding.tvNoHaveFriend.visibility = View.VISIBLE
            }
            if (it["oneSideCount"]!! > 0) {
                list.apply {
                    add(ListItem.HeaderItem(getString(R.string.unapproved_friends)))
                    addAll(viewModel.friends.value!!.one_side!!)
                }
                binding.tvNoHaveFriend.visibility = View.GONE
            }
            if (it["mutualCount"]!! > 0) {
                list.apply {
                    add(ListItem.HeaderItem(getString(R.string.friends_list)))
                    addAll(viewModel.friends.value!!.mutual!!)
                }
                binding.tvNoHaveFriend.visibility = View.GONE
            }
            Log.d("before submit", "current list size=${adapter.itemCount}")
            //TODO::承認/拒否時にリストの並び順がおかしくなった場合、以下のコメントアウトを外して応急対応
//            adapter.submitList(null)
            adapter.submitList(list)
            Log.d("after submit", "current list size=${adapter.itemCount}")
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}