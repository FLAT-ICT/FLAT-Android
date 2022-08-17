package com.websarva.wings.android.flat.ui.startup.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

//import com.websarva.wings.android.flat.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
//    private val viewModel: LoginViewModel by activityViewModels()
//    private var _binding: FragmentLoginBinding? = null
//    private val binding get() = _binding!!
//    private lateinit var input: LoginViewModel.LoginInput

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setContent {
            InputValidationAutoScreen(
                onNavigate = { dest -> findNavController().navigate(dest) }
            )
//            LoginScreen(
//                onNavigate = { dest -> findNavController().navigate(dest) }
//            )
        }
    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.apply {
//            btLogin.setOnSafeClickListener {
//                input = LoginViewModel.LoginInput(
//                    name = etInputNickname.text.toString(),
//                    password = etInputPassword.text.toString(),
//                    isNameOk = false,
//                    isPasswordOk = false
//                )
//                viewModel.checkAndTrim(input)
//            }
//        }
//
//        viewModel.checkResult.observe(viewLifecycleOwner, {
//            input = it
//            if (it.isNameOk) {
//                binding.tilInputNickname.apply {
//                    isErrorEnabled = false
//                }
//            }
//            else if (!it.isNameOk) {
//                binding.tilInputNickname.apply {
//                    error = getString(R.string.input_nickname_error)
//                    isErrorEnabled = true
//                }
//            }
//            if (it.isPasswordOk) {
//                binding.tilInputPassword.apply {
//                    isErrorEnabled = false
//                }
//            }
//            else if (!it.isPasswordOk) {
//                binding.tilInputPassword.apply {
//                    error = getString(R.string.input_password_length_error)
//                    isErrorEnabled = true
//                }
//            }
//            if (it.isNameOk && it.isPasswordOk) {
//                viewModel.preLogin(it)
//            }
//        })
//
//        viewModel.preLoginResponse.observe(viewLifecycleOwner, {
//            when (it.code()) {
//                200 -> {
//                    binding.tvLoginError.apply {
//                        visibility = View.GONE
//                    }
//                    if (it.body()!!.others) {
//                        ForceLoginDialogFragment(input).show(childFragmentManager, "dialog")
//                    } else {
//                        viewModel.login(input)
//                    }
//                }
//                404 -> {
//                    binding.tvLoginError.apply {
//                        visibility = View.VISIBLE
//                    }
//                }
//                else -> {
//                    Toast.makeText(activity, getString(R.string.connection_error), Toast.LENGTH_SHORT).show()
//                }
//            }
//        })
//
//        // loginの結果を監視し、エラー表示orルームに格納
//        viewModel.loginResponse.observe(viewLifecycleOwner, {
//            when (it.code()) {
//                200 -> {
//                    binding.tvLoginError.apply {
//                        visibility = View.GONE
//                    }
//                    viewModel.insertUserIntoRoom(it.body()!!)
//                }
//                404 -> {
//                    binding.tvLoginError.apply {
//                        visibility = View.VISIBLE
//                    }
//                }
//                else -> {
//                    Toast.makeText(activity, getString(R.string.connection_error), Toast.LENGTH_SHORT).show()
//                }
//            }
//        })
//
//        viewModel.error.observe(viewLifecycleOwner, {
//            Toast.makeText(activity, getString(R.string.connection_error), Toast.LENGTH_SHORT).show()
//        })
//
//        // ルームに格納できたのを確認し、画面遷移
//        viewModel.roomChanged.observe(viewLifecycleOwner, {
//            val action = LoginFragmentDirections.actionLoginFragmentToFriendListFragment()
//            view.findNavController().navigate(action)
//        })
//
//        binding.tvToAccountRegistration.setOnSafeClickListener {
//            val action = LoginFragmentDirections.actionLoginFragmentToAccountRegistrationFragment()
//            view.findNavController().navigate(action)
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}