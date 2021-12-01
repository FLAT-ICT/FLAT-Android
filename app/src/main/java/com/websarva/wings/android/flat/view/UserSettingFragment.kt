package com.websarva.wings.android.flat.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.databinding.FragmentLoginBinding
import com.websarva.wings.android.flat.databinding.FragmentUserSettingBinding
import com.websarva.wings.android.flat.viewmodel.LoginViewModel
import com.websarva.wings.android.flat.viewmodel.UserSettingViewModel

class UserSettingFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModels()
    private var _binding: FragmentUserSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btLogout.setOnSafeClickListener {
                //TODO: logout
            }
        }
    }

}