package com.websarva.wings.android.flat.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.websarva.wings.android.flat.databinding.FragmentAccountRegistrationBinding
import com.websarva.wings.android.flat.viewmodel.AccountRegistrationViewModel

class AccountRegistrationFragment : Fragment() {
    private val viewModel: AccountRegistrationViewModel by viewModels()
    private var _binding: FragmentAccountRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btRegister.setOnSafeClickListener{
                val name = etInputNickname.text.toString()
                val password = etInputPassword.text.toString()
                //TODO: ここで2つ目のパスワードも取得し、viewModel.checkPassword()を走らせる
                viewModel.onRegisterButtonClicked(name, password)
            }
        }

        viewModel.userData.observe(viewLifecycleOwner, {
            viewModel.insertUserData()
        })
    }
}