package com.example.homework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        start_implicit_activity.setOnClickListener {
            val intent = Intent()
            intent.apply {
                action = Intent.ACTION_SEND
                type = "text/plane"
                putExtra(
                    "text",
                    if(send_text.text.toString() != "") send_text.text.toString() else "Вы не ввели текст"
                )
            }

            startActivityForResult(intent,1)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) return
        catch_data.text = data.getStringExtra("response")
    }
}