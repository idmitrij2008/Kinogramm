package com.example.kinogramm.view.exit

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.kinogramm.R

class ExitDialogFragment : DialogFragment() {
    private val viewModel: ExitViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage(R.string.confirm_exit)
            .setPositiveButton(R.string.ok) { _, _ ->
                viewModel.exitApp()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
        return builder.create()
    }
}