package com.example.homework

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_takes.*

class TakesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_takes)

        catched_text.text = intent.extras?.getString("text","")

        send_response.setOnClickListener {
            val intent = Intent()
            intent.putExtra(
                "response",
                if(response_text.text.toString() != "") response_text.text.toString() else "Вы не ввели ответ"
            )
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}