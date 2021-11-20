package com.websarva.wings.android.flat.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.databinding.FragmentUnapprovedFriendsBinding
import com.websarva.wings.android.flat.viewmodel.FriendListViewModel

class UnapprovedFriendsFragment : Fragment() {

    private val viewModel: FriendListViewModel by viewModels()
    private var _binding: FragmentUnapprovedFriendsBinding? = null
    private val binding get() = _binding!!
    private lateinit var unapprovedFriendsAdapter: UnapprovedFriendsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUnapprovedFriendsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvUnapprovedFriends.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = UnapprovedFriendsAdapter(childFragmentManager, viewLifecycleOwner, viewModel).also {
                unapprovedFriendsAdapter = it
            }
        }

        viewModel.friends.observe(viewLifecycleOwner, {
            when {
                it.one_side.isEmpty() -> {
                    binding.apply {
                        //TODO: 未承認の友だちがいないとき
                    }
                }
                else -> {
                    unapprovedFriendsAdapter.submitList(it.one_side)
                }
            }
        })
    }
}