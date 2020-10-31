package com.example.homework.dfffutils

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.example.homework.consts.Consts
import com.example.homework.models.Singer

class SingerDiffCallback(
    private val oldList: ArrayList<Singer>,
    private val newList: ArrayList<Singer>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int =
        oldList.size

    override fun getNewListSize(): Int =
        newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val bundle = Bundle().apply {
            if (oldList[oldItemPosition].like != newList[newItemPosition].like) {
                putInt(Consts.KEY_PHOTO, newList[newItemPosition].like)
            }
        }
        return if (bundle.isEmpty) null else bundle
    }

}