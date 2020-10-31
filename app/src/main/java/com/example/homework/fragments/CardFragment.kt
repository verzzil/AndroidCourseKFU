package com.example.homework.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.homework.R
import com.example.homework.adapters.CardAdapter
import com.example.homework.data.Cards
import kotlinx.android.synthetic.main.card_fragment.*

class CardFragment : Fragment() {

    private var adapter: CardAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.card_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CardAdapter(Cards.cards)

        cards_recycler.adapter = adapter
    }
}