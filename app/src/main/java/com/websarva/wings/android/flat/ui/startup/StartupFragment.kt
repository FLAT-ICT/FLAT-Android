package com.websarva.wings.android.flat.ui.startup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController

class StartupFragment : Fragment() {

//    private val viewModel: StartupViewModel by viewModels()
//    val user = viewModel.user.value


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setContent {
//            if (user != null) {
//                Log.d("StartupFragment", "user is not null")
//            }
//            Log.d("UserId in Fragment", "${viewModel.user}")
            StartupScreen(
                onNavigate = { dest -> findNavController().navigate(dest) },
            )
        }
    }
}

