package com.example.homework.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.homework.holder.TabHolder
import com.example.homework.models.Tab

class TabAdapter : ListAdapter<Tab, TabHolder>(object : DiffUtil.ItemCallback<Tab>() {
    override fun areItemsTheSame(oldItem: Tab, newItem: Tab): Boolean =
        oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: Tab, newItem: Tab): Boolean =
        oldItem == newItem

}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabHolder =
        TabHolder(parent)

    override fun onBindViewHolder(holder: TabHolder, position: Int) =
        holder.bind(getItem(position))

}