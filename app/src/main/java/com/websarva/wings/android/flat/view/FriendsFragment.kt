package com.websarva.wings.android.flat.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.databinding.FragmentFriendsBinding
import com.websarva.wings.android.flat.viewmodel.FriendListViewModel

class FriendsFragment : Fragment() {

    private val viewModel: FriendListViewModel by activityViewModels()
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

        binding.friendsProgress.visibility = View.VISIBLE
        binding.rvFriends.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = FriendsAdapter(viewLifecycleOwner, viewModel).also {
                friendsAdapter = it
            }
        }

        viewModel.friends.observe(viewLifecycleOwner, {
            binding.friendsProgress.visibility = View.GONE
            when {
                it.mutual.isEmpty() -> {
                    binding.rvFriends.visibility = View.INVISIBLE
                    if (it.one_side.isEmpty()) {
                        binding.apply {
                            ivRightArrow.visibility = View.GONE
                            tvSomeUnapprovedFriends.visibility = View.GONE
                            tvNoHaveFriend.visibility = View.VISIBLE
                        }
                    } else {
                        binding.apply {
                            tvNoHaveFriend.visibility = View.GONE
                            tvSomeUnapprovedFriends.apply {
                                text = "友だち申請が${it.one_side.size}件届いています\n未承認タブに移動しましょう"
                                visibility = View.VISIBLE
                            }
                            ivRightArrow.visibility = View.VISIBLE
                        }
                    }
                }
                else -> {
                    binding.apply {
                        ivRightArrow.visibility = View.GONE
                        tvNoHaveFriend.visibility = View.GONE
                        tvSomeUnapprovedFriends.visibility = View.GONE
                        rvFriends.visibility = View.VISIBLE
                    }
                }
            }
            friendsAdapter.submitList(it.mutual)
        })

        viewModel.getFriendsCode.observe(viewLifecycleOwner, {
            Log.d("getFriendCode", "$it")
            if (it != 200) {
                Toast.makeText(activity, R.string.connection_error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFriends()
    }
}