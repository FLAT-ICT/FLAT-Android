package com.websarva.wings.android.flat.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.databinding.FragmentFriendListBinding
import com.websarva.wings.android.flat.viewmodel.FriendListViewModel
import com.websarva.wings.android.flat.viewmodel.ListItem

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

        binding.fabAddFriend.setOnClickListener {
            val action =
                FriendListFragmentDirections
                    .actionFriendListFragmentToAddFriendFragment()
            view.findNavController().navigate(action)
        }

        //TODO::データが無いときの判断をどのタイミングでどう行うかを考える
        val list = ArrayList<ListItem>()
        val adapter = FriendListAdapter(viewModel, list)
        binding.apply {
            rvFriendList.adapter = adapter
            rvFriendList.layoutManager = LinearLayoutManager(context)
        }

        viewModel.getFriendsCode.observe(viewLifecycleOwner, Observer {
            when (it) {
                200 -> binding.tvFriendListError.visibility = View.GONE
                else -> binding.tvFriendListError.visibility = View.VISIBLE
            }
        })

        viewModel.friends.observe(viewLifecycleOwner, Observer {
            Log.d("friends", "${viewModel.friends.value}")
            Log.d("oneSide", "${viewModel.oneSideFriends.value}")
            Log.d("mutual", "${viewModel.mutualFriends.value}")
            if (it[0] == 0 && it[1] == 0 && viewModel.oneSideFriends.value != null && viewModel.mutualFriends.value != null) binding.tvNoHaveFriend.visibility =
                View.VISIBLE
            if (it[0] > 0) {
                adapter.addItem(ListItem.HeaderItem(getString(R.string.unapproved_friends)))
                adapter.addItemList(viewModel.oneSideFriends.value!!)
                binding.tvNoHaveFriend.visibility = View.GONE
            }
            if (it[1] > 0) {
                adapter.addItem(ListItem.HeaderItem(getString(R.string.friends_list)))
                adapter.addItemList(viewModel.mutualFriends.value!!)
                binding.tvNoHaveFriend.visibility = View.GONE
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}