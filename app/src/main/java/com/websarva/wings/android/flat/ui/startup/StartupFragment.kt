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
import androidx.navigation.fragment.findNavController

class StartupFragment : Fragment() {

    private val viewModel: StartupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {

        setContent {
            observeIsLoggedIn()
            StartupScreen(
                onNavigate = {dest -> findNavController().navigate(dest)},
            /*navController = findNavController()*/)
        }
    }

    private fun observeIsLoggedIn(){
        viewModel.user.observe(viewLifecycleOwner) {
            if (it != null) {
                if(it.myId != 0){
                    findNavController().navigate(StartupFragmentDirections.actionStartupFragmentToFriendListFragment())
                }
            }
        }
    }

}

