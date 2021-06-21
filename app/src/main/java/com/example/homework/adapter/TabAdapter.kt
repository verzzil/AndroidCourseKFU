package com.example.homework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.homework.R
import com.example.homework.holder.TabHolder
import com.example.homework.models.Tab

class TabAdapter(
    private val click: (tab: Tab) -> Unit,
    private val removeClick: (id: Int) -> Unit,
    private val longTouch: (desc: String) -> Unit
) : ListAdapter<Tab, TabHolder>(object : DiffUtil.ItemCallback<Tab>() {
    override fun areItemsTheSame(oldItem: Tab, newItem: Tab): Boolean =
        oldItem.desc == newItem.desc || oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: Tab, newItem: Tab): Boolean =
        oldItem == newItem

}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabHolder =
        TabHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false),
            click,
            removeClick,
            longTouch
        )

    override fun onBindViewHolder(holder: TabHolder, position: Int) =
        holder.bind(getItem(position))

    override fun submitList(list: List<Tab>?) =
        super.submitList(if (list != null) ArrayList(list) else list)

}