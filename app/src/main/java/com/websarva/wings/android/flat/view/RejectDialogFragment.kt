package com.websarva.wings.android.flat.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.viewmodel.FriendListViewModel

class RejectDialogFragment(private val itemId: Int): DialogFragment() {
    private val viewModel: FriendListViewModel by activityViewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext()).apply {
            setMessage(getString(R.string.dialog_message))
            setPositiveButton(getString(R.string.reject)) { _, _ ->
                viewModel.postRejectFriend(itemId)
            }
            setNegativeButton(getString(R.string.cancel), null)
        }
        return builder.create()
    }
}