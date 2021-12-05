package com.websarva.wings.android.flat.view

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.viewmodel.UserSettingViewModel

class LogoutDialogFragment(private val myId: Int): DialogFragment() {
    private val viewModel: UserSettingViewModel by activityViewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext()).apply {
            setMessage(getString(R.string.logout_dialog_message))
            setPositiveButton(getString(R.string.to_logout)) { _, _ ->
                viewModel.logout(myId)
            }
            setNegativeButton(getString(R.string.cancel), null)
        }
        return builder.create()
    }
}