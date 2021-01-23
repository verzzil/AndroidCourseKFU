package com.example.homework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.homework.adapter.TabAdapter
import com.example.homework.consts.Consts
import com.example.homework.dao.TabDao
import com.example.homework.models.Tab
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main

    private lateinit var adapter: TabAdapter
    private lateinit var db: DataBase
    private lateinit var dbDao: TabDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = DataBase(this)
        dbDao = db.getTabDAO()

//        launch {
//            dbDao.save(Tab("asdf","asdf",123L,0.0,0.0))
//        }

        adapter = TabAdapter(
            {
                val intent = Intent(this, AddEditActivity::class.java)
                intent.apply {
                    putExtra("tab", it)
                    putExtra("mode", Consts.OPEN_WITH_EDIT)
                }
                startActivityForResult(intent, 1)
            },
            {
                launch {
                    dbDao.deleteTabById(it)

                    adapter.submitList(dbDao.getTabs())
                }
            },
            {
                Toast.makeText(this, "$it", Toast.LENGTH_LONG).show()
            }
        )

        launch {
            adapter.submitList(dbDao.getTabs())
            rv_list.adapter = adapter
        }


        open_add.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            intent.apply {
                putExtra("mode", Consts.OPEN_WITH_ADD)
            }
            startActivityForResult(intent, 1)
        }

    }

    override fun onDestroy() {
        coroutineContext.cancel()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) return
        if (requestCode == 1) {
            launch {
                adapter.submitList(dbDao.getTabs())
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.title) {
            "delete" -> {
                launch {
                    dbDao.deleteAll()

                    adapter.submitList(ArrayList())
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }


}