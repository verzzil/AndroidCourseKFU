package com.example.homework

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.homework.consts.Consts
import com.example.homework.models.Tab
import kotlinx.android.synthetic.main.activity_add_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext


class AddEditActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main

    private lateinit var db: DataBase
    private var tabDate: Long = 0
    private var longitude: Double? = null
    private var latitude: Double? = null

    private val dateFormat = SimpleDateFormat("dd.MM.Y")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        detectCoords()

        db = DataBase(this)
        val dbDao = db.getTabDAO()

        val openMode = intent.extras?.getInt("mode")
        if (openMode == Consts.OPEN_WITH_ADD) {
            edit_tab.visibility = View.GONE
            add_tab.visibility = View.VISIBLE
            add_latitude.text = latitude?.toString() ?: "0"
            add_longitude.text = longitude?.toString() ?: "0"

            add_send.setOnClickListener {
                if (add_title.text.isNotEmpty() && add_desc.text.isNotEmpty()) {
                    launch {
                        var newTab = Tab(
                            add_title.text.toString(),
                            add_desc.text.toString(),
                            tabDate,
                            longitude ?: 0.0,
                            latitude ?: 0.0
                        )

                        dbDao.save(newTab)

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
            edit_latitude.text = latitude?.toString() ?: "0"
            edit_longitude.text = longitude?.toString() ?: "0"
            edit_ch_date.text = dateFormat.format(tab.date)

            edit_send.setOnClickListener {
                if (edit_title.text.isNotEmpty() && edit_desc.text.isNotEmpty()) {
                    if (tabDate == 0L) {
                        launch {
                            tab.title = edit_title.text.toString()
                            tab.desc = edit_desc.text.toString()
                            tab.latitude = edit_latitude.text.toString().toDouble()
                            tab.longitude = edit_longitude.text.toString().toDouble()

                            dbDao.update(tab)
                            val intent = Intent()
                            intent.apply {
                                putExtra("ready", true)
                            }
                            AddEditActivity@ setResult(Activity.RESULT_OK, intent)
                            AddEditActivity@ finish()
                        }
                    } else {
                        launch {
                            tab.title = edit_title.text.toString()
                            tab.desc = edit_desc.text.toString()
                            tab.latitude = edit_latitude.text.toString().toDouble()
                            tab.longitude = edit_longitude.text.toString().toDouble()
                            tab.date = tabDate

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

        edit_date.setOnClickListener {
            openDatePicker(
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar[Calendar.YEAR] = year
                    calendar[Calendar.MONTH] = monthOfYear
                    calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                    edit_ch_date.text = formatDate(calendar.timeInMillis)
                }
            )

        }
        add_date.setOnClickListener {
            openDatePicker(
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar[Calendar.YEAR] = year
                    calendar[Calendar.MONTH] = monthOfYear
                    calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                    add_ch_date.text = formatDate(calendar.timeInMillis)
                }
            )
        }


    }

    private fun formatDate(timeStamp: Long): String {
        tabDate = timeStamp
        val tempDate = Date(timeStamp)
        return dateFormat.format(tempDate)
    }

    private fun openDatePicker(listener: DatePickerDialog.OnDateSetListener) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this,
            listener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    @SuppressLint("MissingPermission")
    private fun detectCoords() {
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location: Location? = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        longitude = location?.longitude
        latitude = location?.latitude
    }
}