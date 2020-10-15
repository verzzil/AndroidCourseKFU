package com.example.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var lastPhoto: ImageView? = null
    var selectedIc: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        first.setOnClickListener {
            changeSelected(it)
            changePhoto(photo1)
            photo1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale))
        }

        second.setOnClickListener {
            changeSelected(it)
            changePhoto(photo2)
            photo2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale))
        }

        third.setOnClickListener {
            changeSelected(it)
            changePhoto(photo3)
            photo3.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale))
        }

        fourth.setOnClickListener {
            changeSelected(it)
            changePhoto(photo4)
            photo4.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale))
        }

        fifth.setOnClickListener {
            changeSelected(it)
            changePhoto(photo5)
            photo5.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale))
        }
    }

    private fun changeSelected(currentElement: View) {
        selectedIc?.isSelected = false
        currentElement.isSelected = true
        selectedIc = currentElement as ImageView
    }

    private fun changePhoto(currentPhoto: ImageView) {
        lastPhoto?.visibility = View.INVISIBLE
        currentPhoto.visibility = View.VISIBLE
        lastPhoto = currentPhoto
    }

}