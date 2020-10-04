package com.example.homework

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        sp = getSharedPreferences("App", Context.MODE_PRIVATE)


        login.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                changeAlpha()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                changeAlpha()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        password.setOnFocusChangeListener { view, b ->
            if (b) {
                show_pass.visibility = View.VISIBLE
                var isShow = false
                show_pass.setOnClickListener {
                    if (!isShow) {
                        show_pass.setImageDrawable(getDrawable(R.drawable.ic_eye_cancel))
                        password.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        isShow = !isShow
                    } else {
                        show_pass.setImageDrawable(getDrawable(R.drawable.ic_eye))
                        password.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        isShow = !isShow
                    }
                }
            } else show_pass.visibility = View.INVISIBLE
        }

        entry.setOnClickListener {
            if (it.alpha == 1F) {
                if (sp.getString("login", null) == login.text.toString() &&
                    sp.getString("password", null) == password.text.toString()
                ) {
                    sp.edit().putBoolean("logged", true).apply()

                    val openProfile = Intent(this, MainActivity::class.java)
                    openProfile.apply {
                        putExtra("name", sp.getString("login", null))
                    }

                    startActivity(openProfile)

                } else
                    Toast.makeText(this, "Такого пользователя не сущетвует", Toast.LENGTH_SHORT)
                        .show()
            } else
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show()
        }
    }

    private fun changeAlpha() {
        if (!login.text.isEmpty() && !password.text.isEmpty())
            entry.alpha = 1F
        else
            entry.alpha = .5f
    }
}
