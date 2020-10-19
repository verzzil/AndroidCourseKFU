package com.example.homework

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.activity_liked_posts.*

class LikedPostsActivity : AppCompatActivity() {

    lateinit var likedBrends: ArrayList<Brend>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_liked_posts)

        createAndUpdateLikedList()

        rec_liked.adapter = BrendAdapter(
            likedBrends,
            { brend: Brend, position: Int ->
                if (brend.likeIcon == R.drawable.ic_like) {
                    brend.likeIcon = R.drawable.ic_like_active
                    brend.likes++
                } else {
                    brend.likeIcon = R.drawable.ic_like
                    brend.likes--
                }
                rec_liked.adapter?.notifyItemChanged(position)
            },
            {
                val intent = Intent(this, BrendDisc::class.java)
                intent.apply {
                    putExtra("id", it.id)
                }
                startActivity(intent)
            }
        )


    }

    override fun onRestart() {
        super.onRestart()

        createAndUpdateLikedList()

        rec_liked.adapter = BrendAdapter(
            likedBrends,
            { brend: Brend, position: Int ->
                if (brend.likeIcon == R.drawable.ic_like) {
                    brend.likeIcon = R.drawable.ic_like_active
                    brend.likes++
                } else {
                    brend.likeIcon = R.drawable.ic_like
                    brend.likes--
                }
                rec_liked.adapter?.notifyItemChanged(position)
            },
            {
                val intent = Intent(this, BrendDisc::class.java)
                intent.apply {
                    putExtra("id", it.id)
                }
                startActivity(intent)
            }
        )

    }

    private fun createAndUpdateLikedList() {
        likedBrends = Brends.brends.filter {
                brend -> brend.likeIcon == R.drawable.ic_like_active
        } as ArrayList<Brend>

        if(likedBrends.isEmpty()) {
            val textView = TextView(this)
            textView.apply {
                text = "Вы не выбрали любимые напитки"
                textSize = 20F
                setTextColor(Color.BLACK)
            }
            layout.addView(textView)
        }

    }
}