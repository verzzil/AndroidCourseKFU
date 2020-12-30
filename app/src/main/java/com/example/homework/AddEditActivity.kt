package com.example.homework

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import com.example.homework.consts.Consts
import com.example.homework.models.Tab
import kotlinx.android.synthetic.main.activity_add_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AddEditActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main

    private lateinit var db: DataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        db = DataBase(this)
        val dbDao = db.getTabDAO()

        val openMode = intent.extras?.getInt("mode")
        if (openMode == Consts.OPEN_WITH_ADD) {
            edit_tab.visibility = View.GONE
            add_tab.visibility = View.VISIBLE

            add_send.setOnClickListener {
                if (add_title.text.isNotEmpty() && add_desc.text.isNotEmpty()) {
                    launch {
                        dbDao.save(Tab("${add_title.text}", "${add_desc.text}"))
                        val intent = Intent()
                        intent.apply {
                            putExtra("ready", true)
                        }
                    }
                    AddEditActivity@ setResult(Activity.RESULT_OK, intent)
                    AddEditActivity@ finish()
                }
            }
        } else if (openMode == Consts.OPEN_WITH_EDIT) {
            edit_tab.visibility = View.VISIBLE
            add_tab.visibility = View.GONE

            val tab = intent.extras?.getSerializable("tab") as Tab

            edit_title.text = Editable.Factory.getInstance().newEditable(tab.title)
            edit_desc.text = Editable.Factory.getInstance().newEditable(tab.desc)

            edit_send.setOnClickListener {
                if (edit_title.text.isNotEmpty() && edit_desc.text.isNotEmpty()) {
                    launch {
                        tab.title = edit_title.text.toString()
                        tab.desc = edit_desc.text.toString()
                        dbDao.update(tab)
                        val intent = Intent()
                        intent.apply {
                            putExtra("ready", true)
                        }
                        AddEditActivity@ setResult(Activity.RESULT_OK, intent)
                        AddEditActivity@ finish()
                    }
                }
            }
        }
    }
}