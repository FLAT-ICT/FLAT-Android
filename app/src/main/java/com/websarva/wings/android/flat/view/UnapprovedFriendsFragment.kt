package com.websarva.wings.android.flat.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.databinding.FragmentUnapprovedFriendsBinding
import com.websarva.wings.android.flat.viewmodel.FriendListViewModel

class UnapprovedFriendsFragment : Fragment() {

    private val viewModel: FriendListViewModel by activityViewModels()
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

        binding.rvUnapprovedFriends.apply {
            layoutManager = LinearLayoutManager(context)
            adapter =
                UnapprovedFriendsAdapter(childFragmentManager, viewLifecycleOwner, viewModel).also {
                    unapprovedFriendsAdapter = it
                }
        }

        viewModel.friends.observe(viewLifecycleOwner, {
            when {
                it.one_side.isEmpty() -> {
                    binding.tvNoUnapprovedFriend.visibility = View.VISIBLE
                }
                else -> {
                    binding.tvNoUnapprovedFriend.visibility = View.GONE
                }
            }
            unapprovedFriendsAdapter.submitList(it.one_side)
        })

        viewModel.operationUnapprovedFriends.observe(viewLifecycleOwner, {
            when(it[1]) {
                200 -> {
                    Log.d("Approve or Reject",
                        if (it[0] == 0) "ApproveSuccess" else "RejectSuccess"
                    )
                    viewModel.getFriends()
                }
                else -> {
                    Toast.makeText(activity, R.string.connection_error.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFriends()
    }
}