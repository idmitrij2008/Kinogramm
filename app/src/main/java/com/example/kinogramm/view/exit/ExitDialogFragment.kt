package com.example.kinogramm.view.exit

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.kinogramm.R
import com.example.kinogramm.view.KinogrammApp
import com.example.kinogramm.view.ViewModelFactory
import javax.inject.Inject

@ExperimentalPagingApi
class ExitDialogFragment : DialogFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ExitViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ExitViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as KinogrammApp).component
            .activityComponentFactory()
            .create(0)
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

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