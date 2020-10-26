package com.example.homework.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.homework.R
import java.lang.ClassCastException

class AddNewItemDialogFragment : DialogFragment() {
    interface DialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
    }

    lateinit var mListener: DialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = context as DialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("${context} must implement DialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = android.app.AlertDialog.Builder(activity)
        var inflater = activity?.layoutInflater
        var dialogView = inflater?.inflate(R.layout.add_new_item_dialog, null)

        builder
            .setView(dialogView)
            .setNegativeButton("Cancel") { _, _ ->
                this.dialog?.cancel()
            }
            .setPositiveButton("Send") { _, _ ->
                mListener.onDialogPositiveClick(this)
            }

        return builder.create()
    }

}