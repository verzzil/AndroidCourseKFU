package com.example.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var lastPhoto : ImageView? = null
        var selectedIc : ImageView? = null

        first.setOnClickListener {
            selectedIc?.isSelected = false
            it.isSelected = true
            selectedIc = it as ImageView

            lastPhoto?.visibility = View.INVISIBLE
            photo1.visibility = View.VISIBLE
            lastPhoto = photo1

        }

        second.setOnClickListener {
            selectedIc?.isSelected = false
            it.isSelected = true
            selectedIc = it as ImageView

            lastPhoto?.visibility = View.INVISIBLE
            photo2.visibility = View.VISIBLE
            lastPhoto = photo2

        }
    }
}