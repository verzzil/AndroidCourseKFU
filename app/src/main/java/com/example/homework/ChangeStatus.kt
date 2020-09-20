package com.example.homework

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_change_status.*
import java.lang.ClassCastException


class ChangeStatus : DialogFragment() {
    interface DialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
    }

    lateinit var mListener: DialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = context as DialogListener
        }
        catch (e: ClassCastException) {
            throw ClassCastException("${context} must implement DialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = android.app.AlertDialog.Builder(activity)
        var inflater = activity?.layoutInflater
        var dialogView = inflater?.inflate(R.layout.dialog_change_status, null)


        builder .setView(dialogView)
            .setNegativeButton("Отменить",
                DialogInterface.OnClickListener { dialog, id ->
                    this.dialog?.cancel()
                })
            .setPositiveButton("Применить",
                DialogInterface.OnClickListener { dialog, id ->
                    mListener.onDialogPositiveClick(this)
                })

        return builder.create()
    }
}