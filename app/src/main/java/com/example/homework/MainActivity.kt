package com.example.homework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recycler_item.view.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        rec.adapter = BrendAdapter( Brends.brends,
            { brend: Brend, position: Int ->
                if (brend.likeIcon == R.drawable.ic_like) {
                    brend.likeIcon = R.drawable.ic_like_active
                    brend.likes++
                } else {
                    brend.likeIcon = R.drawable.ic_like
                    brend.likes--
                }
                rec.adapter?.notifyItemChanged(position)
            },
            {
                val intent = Intent(this, BrendDisc::class.java)
                intent.apply {
                    putExtra("id", it.id)
                }
                startActivity(intent)
            }
        )

        rec.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        show_liked.setOnClickListener {
            val intent = Intent(this, LikedPostsActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onRestart() {
        super.onRestart()
        rec.adapter?.notifyDataSetChanged()
    }
}