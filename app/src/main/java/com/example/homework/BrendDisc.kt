package com.example.homework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_brend_disc.*
import kotlinx.android.synthetic.main.activity_brend_disc.like
import kotlinx.android.synthetic.main.activity_brend_disc.logo
import kotlinx.android.synthetic.main.activity_main.*

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
        like.setImageResource(currentBrend.likeIcon)
        count_likes.text = "${currentBrend.likes}"


        like.setOnClickListener {
            changeLike(currentBrend)
        }
        count_likes.setOnClickListener {
            changeLike(currentBrend)
        }

    }

    private fun changeLike(currentBrend: Brend) {
        if (currentBrend.likeIcon == R.drawable.ic_like) {
            currentBrend.likeIcon = R.drawable.ic_like_active
            currentBrend.likes++
        } else {
            currentBrend.likeIcon = R.drawable.ic_like
            currentBrend.likes--
        }
        like.setImageResource(currentBrend.likeIcon)
        count_likes.text = "${currentBrend.likes}"
    }
}