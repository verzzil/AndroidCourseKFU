package com.example.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.homework.data.Singers
import com.example.homework.fragments.AddNewItemDialogFragment
import com.example.homework.fragments.CardFragment
import com.example.homework.fragments.PlugFragment
import com.example.homework.fragments.RecyclerFragment
import com.example.homework.models.Singer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_new_item_dialog.*

class MainActivity : AppCompatActivity(), AddNewItemDialogFragment.DialogListener {

    lateinit var lastFragment: Fragment
    lateinit var recyclerFragment: RecyclerFragment
    lateinit var plugFragment: PlugFragment
    lateinit var cardFragment: CardFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentId = R.id.frame_layout
        plugFragment = PlugFragment()
        recyclerFragment = RecyclerFragment()
        cardFragment = CardFragment()
        supportFragmentManager.beginTransaction()
            .add(fragmentId, plugFragment)
            .add(fragmentId, recyclerFragment)
            .add(fragmentId, cardFragment)
            .hide(recyclerFragment)
            .hide(cardFragment)
            .commit()
        lastFragment = plugFragment

        bottom_nav_bar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.page_1 -> {
                    changeFragment(plugFragment)
                    lastFragment = plugFragment
                    true
                }
                R.id.page_2 -> {
                    changeFragment(recyclerFragment)
                    lastFragment = recyclerFragment
                    true
                }
                R.id.page_3 -> {
                    changeFragment(cardFragment)
                    lastFragment = cardFragment
                    true
                }
                else -> false
            }
        }

    }

    private fun changeFragment(newFragment: Fragment) {
        if (newFragment != lastFragment)
            if (
                newFragment == plugFragment && (lastFragment == recyclerFragment || lastFragment == cardFragment) ||
                newFragment == recyclerFragment && lastFragment == cardFragment
            )
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.animator.show_from_left, R.animator.hide_from_right)
                    .hide(lastFragment)
                    .show(newFragment)
                    .commit()
            else
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.animator.show_from_right, R.animator.hide_from_left)
                    .hide(lastFragment)
                    .show(newFragment)
                    .commit()
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        val title = dialog.dialog?.input_title?.text.toString()
        val description = dialog.dialog?.input_description?.text.toString()
        val position = dialog.dialog?.input_position?.text.toString()

        val tempArr = Singers.cloneData()

        if (
            position == "" ||
            tempArr.size <= position.toInt() ||
            position.toInt() <= 0
        )
            tempArr.add(
                Singer(
                    Singers.findMoreId(),
                    title,
                    description,
                    R.drawable.kat,
                    R.drawable.ic_like
                )
            )
        else
            tempArr.add(
                position.toInt() - 1,
                Singer(Singers.findMoreId(), title, description, R.drawable.kat, R.drawable.ic_like)
            )

        recyclerFragment.updateList(tempArr)
    }
}