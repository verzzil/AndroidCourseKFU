package com.example.homework

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_change_status.*

class MainActivity : AppCompatActivity(), ChangeStatus.DialogListener {
    lateinit var sp: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sp = getSharedPreferences("Status", Context.MODE_PRIVATE)
        userStatus.text = sp.getString("status","nulled")

        editStatus.setOnClickListener {
            var changeDialogStatus = ChangeStatus()
            changeDialogStatus.show(supportFragmentManager, "new_status")
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        userStatus.text = dialog.dialog?.newStatus?.text ?: "Alba"
        sp.edit().apply {
            putString("status", dialog.dialog?.newStatus?.text.toString() ?: "Alba")
            apply()
        }
    }

}