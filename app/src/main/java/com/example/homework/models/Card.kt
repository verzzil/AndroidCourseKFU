package com.example.homework.models

import com.example.homework.adapters.ViewPagerAdapter

data class Card(
    val userPhoto: Int,
    val userName: String,
    val description: String,
    val viewPager2: ViewPagerAdapter
) {
}