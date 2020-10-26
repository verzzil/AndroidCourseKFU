package com.example.homework.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.homework.R
import com.example.homework.data.Singers
import com.example.homework.dfffutils.SingerDiffCallback
import com.example.homework.holders.SingerHolder
import com.example.homework.models.Singer

class SingerAdapter(
    var list: ArrayList<Singer>,
    private val likeClick: (Int) -> Unit,
    private val removeClick: (Int) -> Unit
) : RecyclerView.Adapter<SingerHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingerHolder =
        SingerHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false),
            likeClick,
            removeClick
        )

    override fun getItemCount(): Int =
        list.size

    override fun onBindViewHolder(holder: SingerHolder, position: Int) =
        holder.bind(list[position])

    override fun onBindViewHolder(holder: SingerHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else {
            (payloads[0] as? Bundle)?.also {
                holder.updateFields(it)
            }
        }
    }

    fun updateDataSource(newList: ArrayList<Singer>) {
        if(newList != list) {
            val callback = SingerDiffCallback(list, newList)
            val result = DiffUtil.calculateDiff(callback, true)
            result.dispatchUpdatesTo(this)

            list = newList
            Singers.singers = newList
        }
    }
}