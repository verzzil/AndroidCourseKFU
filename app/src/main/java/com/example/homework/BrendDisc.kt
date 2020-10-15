package com.example.homework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_brend_disc.*

class BrendDisc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brend_disc)

        val currentId = intent.getIntExtra("id", 0)
        var currentBrend: Brend? = null

        for(brend: Brend in Brends.brends) {
            if(brend.id == currentId) {
                currentBrend = brend
            }
        }

        logo.setImageResource(currentBrend!!.logo)
        title_full.text = currentBrend.title
        disc.text = currentBrend.fullDisc

    }
}