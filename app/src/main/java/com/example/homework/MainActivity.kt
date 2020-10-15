package com.example.homework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        rec.adapter = BrendAdapter() {
            val intent = Intent(this, BrendDisc::class.java)
            intent.apply {
                putExtra("id", it.id)
            }
            startActivity(intent)
        }

    }
}