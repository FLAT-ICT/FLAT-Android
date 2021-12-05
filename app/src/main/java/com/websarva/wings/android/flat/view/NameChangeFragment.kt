package com.websarva.wings.android.flat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.websarva.wings.android.flat.databinding.FragmentNameChangeBinding
import com.websarva.wings.android.flat.viewmodel.UserSettingViewModel


class NameChangeFragment : Fragment() {
    private val viewModel: UserSettingViewModel by activityViewModels()
    private var _binding: FragmentNameChangeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNameChangeBinding.inflate(inflater, container, false)
        return binding.root
    }
}