package com.example.weatherapp.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.weatherapp.R

class DeleteCardDialogFragment(val listener: MyDialogListener): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.dialog_delete_card, null)
            builder.setView(view)
                // Add action buttons
                .setPositiveButton(
                    "DELETE",
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.onOkClicked()
                    })
                .setNegativeButton(
                    "CANCEL",
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
    interface MyDialogListener {
        fun onOkClicked()

    }
}