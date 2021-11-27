package com.websarva.wings.android.flat.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.websarva.wings.android.flat.databinding.FragmentStartupBinding
import com.websarva.wings.android.flat.viewmodel.StartupViewModel

class StartupFragment : Fragment() {

    private val viewModel: StartupViewModel by viewModels()
    private var _binding: FragmentStartupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btLogin.setOnSafeClickListener {
            val action = StartupFragmentDirections.actionStartupFragmentToLoginFragment()
            view.findNavController().navigate(action)
        }

        binding.btNewAccountRegistration.setOnSafeClickListener {
            val action = StartupFragmentDirections.actionStartupFragmentToAccountRegistrationFragment()
            view.findNavController().navigate(action)
        }
    }
}