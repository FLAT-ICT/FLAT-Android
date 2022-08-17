package com.websarva.wings.android.flat.ui.startup.login_signup

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.websarva.wings.android.flat.R

class ForceLoginDialogFragment(private val input: LoginViewModel.LoginInput) : DialogFragment() {
    private val viewModel: LoginViewModel by activityViewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext()).apply {
            setMessage(getString(R.string.force_login_dialog_message))
            setPositiveButton(getString(R.string.to_login)) { _, _ ->
                viewModel.login(input)
            }
            setNegativeButton(getString(R.string.cancel), null)
        }
        return builder.create()
    }
}