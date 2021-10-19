package com.websarva.wings.android.flat.view

import android.os.Bundle
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

        binding.fabAddFriend.setOnClickListener{
            val action =
                FriendListFragmentDirections
                    .actionFriendListFragmentToAddFriendFragment()
            view.findNavController().navigate(action)
        }

        //TODO::動くかどうかの確認に書いただけ。後で消す
        //TODO::データが無いときの判断をどのタイミングでどう行うかを考える
        val list = ArrayList<ListItem>()
        val adapter = FriendListAdapter(list)
        binding.apply {
            rvFriendList.adapter = adapter
            rvFriendList.layoutManager = LinearLayoutManager(context)
        }
        viewModel.oneSideFriends.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                adapter.addItem(ListItem.HeaderItem(getString(R.string.unapproved_friends)))
                adapter.addItemList(it)
            }
        })
        viewModel.mutualFriends.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                adapter.addItem(ListItem.HeaderItem(getString(R.string.friends_list)))
                adapter.addItemList(it)
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}