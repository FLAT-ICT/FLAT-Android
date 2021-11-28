package com.websarva.wings.android.flat.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.websarva.wings.android.flat.BeaconDetectionService
import com.websarva.wings.android.flat.R
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
            btRegister.setOnSafeClickListener {
                val input = AccountRegistrationViewModel.UserInputData(
                    name = etInputNickname.text.toString(),
                    pass1 = etInputPassword.text.toString(),
                    pass2 = etReInputPassword.text.toString(),
                    isMatch = false,
                    isCharaLenOk = false
                )
                viewModel.checkMatchPassword(input)
            }
        }

        viewModel.isMatchPassword.observe(viewLifecycleOwner, {
            viewModel.checkCharacter(it)
            if (it.isMatch) {
                // エラーを消す
                binding.tvAccountRegistrationError.apply {
                    visibility = View.GONE
                }
            } else {
                // エラーを出す
                binding.tvAccountRegistrationError.apply {
                    text = getString(R.string.password_mismatch_error)
                    visibility = View.VISIBLE
                }
            }
        })

        viewModel.isCharacterOk.observe(viewLifecycleOwner, {
            if (it.isCharaLenOk) {
                viewModel.checkPasswordLength(it)
            } else {
                // エラーを出す
                binding.tilInputPassword.apply {
                    error = getString(R.string.input_character_error)
                    isErrorEnabled = true
                }
            }
        })

        viewModel.isLengthOk.observe(viewLifecycleOwner, {
            if (it.isCharaLenOk) {
                // エラーを消す
                    binding.tilInputPassword.apply {
                        isErrorEnabled = false
                    }
                if (it.isMatch) {
                    // postする
                    viewModel.registerUser(it)
                }
            } else {
                // 長さのエラーを出す
                binding.tilInputPassword.apply {
                    error = getString(R.string.input_password_length_error)
                    isErrorEnabled = true
                }
            }
        })

        //TODO: レスポンスメッセージを監視し、名前重複のエラーメッセージを表示するorログイン
        //TODO: ログインする場合、ログイン前にroomに保存

        viewModel.userData.observe(viewLifecycleOwner, {
            viewModel.registerUserInRoom()
        })
    }
}