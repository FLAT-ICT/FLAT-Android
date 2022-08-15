package com.websarva.wings.android.flat.ui.startup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.websarva.wings.android.flat.FLATApplication
import com.websarva.wings.android.flat.databinding.FragmentStartupBinding
import com.websarva.wings.android.flat.view.setOnSafeClickListener

class StartupFragment : Fragment() {

    private val viewModel: StartupViewModel by viewModels()
    private var _binding: FragmentStartupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setContent {
            StartupScreen(/*navController = findNavController()*/)
        }
    }
//    {
//        _binding = FragmentStartupBinding.inflate(inflater, container, false).apply {
////            composeView.setContent{}
//        }
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Login時にmyId設定，FriendListFragmentに遷移させる
//        // Logout処理時に0にする．IDはサーバーの実装的に1スタート．実装が微妙だと思ったら書き換える
//        if (FLATApplication.myId != 0) {
//            val action = StartupFragmentDirections.actionStartupFragmentToFriendListFragment2()
//            view.findNavController().navigate(action)
//        }
//
//        binding.btLogin.setOnSafeClickListener {
//            val action = StartupFragmentDirections.actionStartupFragmentToLoginFragment()
//            view.findNavController().navigate(action)
//        }
//
//        binding.btNewAccountRegistration.setOnSafeClickListener {
//            val action = StartupFragmentDirections.actionStartupFragmentToAccountRegistrationFragment()
//            view.findNavController().navigate(action)
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}

