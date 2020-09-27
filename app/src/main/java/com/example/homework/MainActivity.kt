package com.example.homework

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_change_status.*

class MainActivity : AppCompatActivity(), ChangeStatus.DialogListener {
    lateinit var sp: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sp = getSharedPreferences("App", Context.MODE_PRIVATE)


        fillSharedPref()

        isLogged()

        fillUserStatus()

        if(intent.extras?.getString("name") == null)
            userName.text = sp.getString("login",null)
        else
            userName.text = intent.extras?.getString("name")

        editStatus.setOnClickListener {
            val changeDialogStatus = ChangeStatus()
            changeDialogStatus.show(supportFragmentManager, "new_status")
        }


        quit.setOnClickListener {
            sp.edit().putBoolean("logged", false).apply()
            startActivity(Intent(this, SignInActivity::class.java))
        }

    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        userStatus.text = dialog.dialog?.newStatus?.text ?: "Alba"
        sp.edit().apply {
            putString("status", dialog.dialog?.newStatus?.text.toString() ?: "Alba")
            apply()
        }
    }

    private fun fillSharedPref() {
        if (!sp.contains("login")) {
            sp.edit().apply {
                putString("login", "Альберт Ханнанов")
                putString("password", "qwerty007")
                putBoolean("logged", false)
                apply()
            }
        }
    }

    private fun isLogged() {
        if (!sp.getBoolean("logged", false)) startActivity(
            Intent(
                this,
                SignInActivity::class.java
            )
        )
    }

    private fun fillUserStatus() {
        if (sp.contains("status"))
            userStatus.text = sp.getString("status", "nulled")
    }

}