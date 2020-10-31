package com.example.homework.data

import com.example.homework.R
import com.example.homework.adapters.ViewPagerAdapter
import com.example.homework.models.Card

object Cards {
    var cards = arrayListOf(
        Card(
            R.drawable.user_photo1, "verzzil", "The best", ViewPagerAdapter(
                arrayListOf(
                    R.drawable.card1,
                    R.drawable.card2,
                    R.drawable.card3
                )
            )
        ),
        Card(
            R.drawable.user_photo3, "renat_f14", "Hello", ViewPagerAdapter(
                arrayListOf(
                    R.drawable.card4,
                    R.drawable.card5
                )
            )
        )
    )
}