package com.example.weatherapp.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.weatherapp.R

class AddDialogFragment(val listener: MyDialogListener): DialogFragment()  {



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            val view = inflater.inflate(R.layout.dialog_add, null)
            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            val textCity = view.findViewById<TextView>(R.id.text_input_city)


            builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.onOkClicked(view)
                    })
                .setNegativeButton(
                    R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    interface MyDialogListener {
        fun onOkClicked(dialog: View)

    }
}