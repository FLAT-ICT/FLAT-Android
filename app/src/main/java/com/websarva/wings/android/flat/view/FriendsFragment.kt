package com.websarva.wings.android.flat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.websarva.wings.android.flat.databinding.FragmentFriendsBinding
import com.websarva.wings.android.flat.viewmodel.FriendListViewModel

class FriendsFragment : Fragment() {

    private val viewModel: FriendListViewModel by viewModels()
    private var _binding: FragmentFriendsBinding? = null
    private val binding get() = _binding!!
    private lateinit var friendsAdapter: FriendsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFriends.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = FriendsAdapter(viewLifecycleOwner, viewModel).also {
                friendsAdapter = it
            }
        }

        viewModel.friends.observe(viewLifecycleOwner, {
            when {
                it.mutual.isEmpty() -> {
                    binding.apply {
                        //TODO: 友だちがいないとき
                    }
                }
                else -> {
                    friendsAdapter.submitList(it.mutual)
                }
            }
        })
    }
}