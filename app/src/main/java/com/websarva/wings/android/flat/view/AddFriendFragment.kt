package com.websarva.wings.android.flat.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.databinding.FragmentAddFriendBinding
import com.websarva.wings.android.flat.viewmodel.AddFriendViewModel

class AddFriendFragment : Fragment() {
    private val viewModel: AddFriendViewModel by viewModels()
    private var _binding: FragmentAddFriendBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddFriendBinding.inflate(inflater, container, false)
        binding.ibSearchId.setOnClickListener {
            val id = binding.etInputFriendId.text.toString()
            if (binding.etInputFriendId.text?.length == 6)
                viewModel.getUserInfo(id)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.user.observe(viewLifecycleOwner, Observer {
            Log.d("debug", "${viewModel.user.value}")
            binding.tvAddFriendName.text = viewModel.user.value?.name
        })
    }

}