package com.websarva.wings.android.flat.ui.startup.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.websarva.wings.android.flat.databinding.FragmentAccountRegistrationBinding

class SignUpFragment : Fragment() {
    private val viewModel: AccountRegistrationViewModel by viewModels()
    private var _binding: FragmentAccountRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setContent {
            SignUpScreen()
        }
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.apply {
//            btRegister.setOnSafeClickListener {
//                val input = AccountRegistrationViewModel.UserInputData(
//                    name = etInputNickname.text.toString(),
//                    pass1 = etInputPassword.text.toString(),
//                    pass2 = etReInputPassword.text.toString(),
//                    isNameOk = false,
//                    isMatch = false,
//                    isCharaLenOk = false
//                )
//                viewModel.checkAndTrimName(input)
//            }
//        }
//
//        viewModel.trimmedName.observe(viewLifecycleOwner) {
//            viewModel.checkMatchPassword(it)
//            if (it.isNameOk) {
//                binding.tilInputNickname.apply {
//                    isErrorEnabled = false
//                }
//            } else {
//                binding.tilInputNickname.apply {
//                    error = getString(R.string.input_nickname_error)
//                    isErrorEnabled = true
//                }
//            }
//        }
//
//
//        viewModel.isMatchPassword.observe(viewLifecycleOwner) {
//            viewModel.checkCharacter(it)
//            if (it.isMatch) {
//                // エラーを消す
//                binding.tvAccountRegistrationError.apply {
//                    visibility = View.GONE
//                }
//            } else {
//                // エラーを出す
//                binding.tvAccountRegistrationError.apply {
//                    text = getString(R.string.password_mismatch_error)
//                    visibility = View.VISIBLE
//                }
//            }
//        }
//
//        viewModel.isCharacterOk.observe(viewLifecycleOwner) {
//            if (it.isCharaLenOk) {
//                viewModel.checkPasswordLength(it)
//            } else {
//                // エラーを出す
//                binding.tilInputPassword.apply {
//                    error = getString(R.string.input_character_error)
//                    isErrorEnabled = true
//                }
//            }
//        }
//
//        viewModel.isLengthOk.observe(viewLifecycleOwner) {
//            if (it.isCharaLenOk) {
//                // エラーを消す
//                binding.tilInputPassword.apply {
//                    isErrorEnabled = false
//                }
//                if (it.isMatch && it.isNameOk) {
//                    // postする
//                    Log.d("userData", "$it")
//                    viewModel.registerUser(it)
//                }
//            } else {
//                // 長さのエラーを出す
//                binding.tilInputPassword.apply {
//                    error = getString(R.string.input_password_length_error)
//                    isErrorEnabled = true
//                }
//            }
//        }
//
//        // レスポンスを監視し、名前重複のエラーメッセージを表示するorログイン
//        viewModel.postResponse.observe(viewLifecycleOwner) {
//            when (it.code()) {
//                200 -> {
//                    viewModel.registerUserInRoom(it.body()!!)
//                }
//                400 -> {
//                    binding.tvAccountRegistrationError.apply {
//                        text = getString(R.string.already_used_nickname_error)
//                        visibility = View.VISIBLE
//                    }
//                }
//                else -> {
//                    Toast.makeText(
//                        activity,
//                        getString(R.string.connection_error),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        }
//
//        viewModel.registerOk.observe(viewLifecycleOwner) {
//            val action =
//                SignUpFragmentDirections.actionAccountRegistrationFragmentToFriendListFragment()
//            view.findNavController().navigate(action)
//        }
//
//        binding.tvHasAccountToLogin.setOnSafeClickListener{
//            val action = SignUpFragmentDirections.actionAccountRegistrationFragmentToLoginFragment()
//            view.findNavController().navigate(action)
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}