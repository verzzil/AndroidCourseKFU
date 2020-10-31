package com.example.homework.data

import com.example.homework.R
import com.example.homework.models.Singer

object Singers {
    var singers = arrayListOf(
        Singer(27,"Morenshtern","Морген што", R.drawable.morgen, R.drawable.ic_like),
        Singer(12, "Филипп Киркоров", "Филя", R.drawable.filya, R.drawable.ic_like),
        Singer(3, "Дима Билан", "Билайн", R.drawable.bilan, R.drawable.ic_like),
        Singer(40, "Клава Кока", "Кола", R.drawable.koka, R.drawable.ic_like),
        Singer(29, "Егор Крид", "Сладкий мальчик", R.drawable.krid, R.drawable.ic_like),
        Singer(60, "Zivert", "Depression", R.drawable.zivert, R.drawable.ic_like),
        Singer(79, "Тимати", "Black star burger", R.drawable.timati, R.drawable.ic_like)
        )

    fun findIndexById(id: Int) : Int {
        var index = 0
        for(singer : Singer in singers) {
            if(singer.id == id) return index
            index++
        }
        return -1
    }
    fun cloneData() : ArrayList<Singer> {
        val result = ArrayList<Singer>()
        for(singer: Singer in singers) {
            result.add(singer.clone())
        }
        return result
    }
    fun findMoreId(): Int {
        var moreId = 0
        for(singer: Singer in singers)
            if (singer.id > moreId) moreId = singer.id

        return ++moreId
    }
}