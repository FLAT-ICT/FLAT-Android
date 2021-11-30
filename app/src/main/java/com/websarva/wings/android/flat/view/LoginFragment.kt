package com.websarva.wings.android.flat.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.databinding.FragmentLoginBinding
import com.websarva.wings.android.flat.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var input: LoginViewModel.LoginInput

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btLogin.setOnSafeClickListener {
                input = LoginViewModel.LoginInput(
                    name = etInputNickname.text.toString(),
                    password = etInputPassword.text.toString(),
                    isNameOk = false,
                    isPasswordOk = false
                )
                viewModel.checkAndTrim(input)
            }
        }

        viewModel.checkResult.observe(viewLifecycleOwner, {
            input = it
            if (it.isNameOk) {
                binding.tilInputNickname.apply {
                    isErrorEnabled = false
                }
            }
            else if (!it.isNameOk) {
                binding.tilInputNickname.apply {
                    error = getString(R.string.input_nickname_error)
                    isErrorEnabled = true
                }
            }
            if (it.isPasswordOk) {
                binding.tilInputPassword.apply {
                    isErrorEnabled = false
                }
            }
            else if (!it.isPasswordOk) {
                binding.tilInputPassword.apply {
                    error = getString(R.string.input_password_length_error)
                    isErrorEnabled = true
                }
            }
            if (it.isNameOk && it.isPasswordOk) {
                viewModel.preLogin(it)
            }
        })

        viewModel.preLoginResponse.observe(viewLifecycleOwner, {
            when (it.code()) {
                200 -> {
                    binding.tvLoginError.apply {
                        visibility = View.GONE
                    }
                    if (it.body()!!.others) {
                        //TODO: showDialog
                    } else {
                        viewModel.login(input)
                    }
                }
                404 -> {
                    binding.tvLoginError.apply {
                        visibility = View.VISIBLE
                    }
                }
                else -> {
                    Toast.makeText(activity, getString(R.string.connection_error), Toast.LENGTH_SHORT).show()
                }
            }
        })

        // loginの結果を監視し、エラー表示orルームに格納
        viewModel.loginResponse.observe(viewLifecycleOwner, {
            when (it.code()) {
                200 -> {
                    binding.tvLoginError.apply {
                        visibility = View.GONE
                    }
                    //TODO: roomにit.body()を格納
                }
                404 -> {
                    binding.tvLoginError.apply {
                        visibility = View.VISIBLE
                    }
                }
                else -> {
                    Toast.makeText(activity, getString(R.string.connection_error), Toast.LENGTH_SHORT).show()
                }
            }
        })

        //TODO: ルームに格納できたのを確認し、画面遷移

        binding.tvToAccountRegistration.setOnSafeClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToAccountRegistrationFragment()
            view.findNavController().navigate(action)
        }
    }
}