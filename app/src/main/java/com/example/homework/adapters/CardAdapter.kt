package com.example.homework.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework.R
import com.example.homework.holders.CardHolder
import com.example.homework.models.Card

class CardAdapter(
    var cardList: ArrayList<Card>
) : RecyclerView.Adapter<CardHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder =
        CardHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_view_item, parent, false)
        )

    override fun getItemCount(): Int = cardList.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) =
        holder.bind(cardList[position])

}