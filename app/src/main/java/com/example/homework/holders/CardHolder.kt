package com.example.homework.holders

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.homework.R
import com.example.homework.models.Card
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CardHolder(
    containerView: View
) : RecyclerView.ViewHolder(containerView) {

    var userPhoto = itemView.findViewById<ShapeableImageView>(R.id.user_photo)
    var userName = itemView.findViewById<TextView>(R.id.user_name)
    var viewPager2 = itemView.findViewById<ViewPager2>(R.id.view_pager2)
    var cardDescription = itemView.findViewById<TextView>(R.id.card_description)
    var tabs = itemView.findViewById<TabLayout>(R.id.tab_for_pager)
    var carsDescUser = itemView.findViewById<TextView>(R.id.card_desc_user)

    fun bind(card: Card) {
        Log.i("about", "Card bind")
        userPhoto.setImageResource(card.userPhoto)
        userName.text = card.userName
        cardDescription.text = card.description
        carsDescUser.text = card.userName
        viewPager2.adapter = card.viewPager2

        TabLayoutMediator(tabs, viewPager2) { tab, postition -> }.attach()

    }

}