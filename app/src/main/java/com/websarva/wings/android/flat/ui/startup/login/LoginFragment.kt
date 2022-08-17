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
}