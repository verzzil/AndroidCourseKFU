package com.example.homework.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.homework.R
import com.example.homework.adapters.SingerAdapter
import com.example.homework.data.Singers
import com.example.homework.models.Singer
import kotlinx.android.synthetic.main.recycler_fragment.*

class RecyclerFragment : Fragment() {

    private var adapter: SingerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.recycler_fragment, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        adapter = SingerAdapter(
            Singers.singers,
            { index: Int ->
                var tempArr = Singers.cloneData()
                if (tempArr[index].like == R.drawable.ic_like)
                    tempArr[index].like = R.drawable.ic_like_active
                else
                    tempArr[index].like = R.drawable.ic_like


                adapter?.updateDataSource(tempArr)
            },
            { index: Int ->
                var tempArr = Singers.cloneData()
                tempArr.removeAt(index)

                adapter?.updateDataSource(tempArr)
            }
        )

        recycler_singers.adapter = adapter

        swipe.setOnRefreshListener {
            var forRefreshed = Singers.cloneData()
            randomChangeElements(forRefreshed)

            adapter?.updateDataSource(forRefreshed)
            recycler_singers.scrollToPosition(0)
            swipe.isRefreshing = false
        }

        floating_action_btn.setOnClickListener {
            val newItemDialogFragment = AddNewItemDialogFragment()
            newItemDialogFragment.show(fragmentManager!!, "new_item")

            recycler_singers.scrollToPosition(0)
        }


    }

    private fun randomChangeElements(list: ArrayList<Singer>) {
        for (i in 0..list.lastIndex) {
            val rand1 = (0..list.lastIndex).random()
            val rand2 = (0..list.lastIndex).random()
            val temp = list[rand1]
            list[rand1] = list[rand2]
            list[rand2] = temp
        }
    }

    fun updateList(newList: ArrayList<Singer>) {
        adapter?.updateDataSource(newList)
        recycler_singers.scrollToPosition(0)
    }
}