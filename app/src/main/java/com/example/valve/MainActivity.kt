package com.example.valve

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {


    var freshCloseTimeHr = 0
    var freshCloseTimeMi = 0
    var freshOpenTimeHr = 0
    var freshOpenTimeMi = 0
    var trashCloseTimeHr = 0
    var trashCloseTimeMi = 0
    var trashOpenTimeHr = 0
    var trashOpenTimeMi = 0

    var TObool = false
    var TCbool = false
    var FObool = false
    var FCbool = false

    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val decor = getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        database = FirebaseDatabase.getInstance("https://espautovalve-default-rtdb.asia-southeast1.firebasedatabase.app")
        myRef = database.reference


        // Read from the database
        myRef.addValueEventListener(object: ValueEventListener {

            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.child("test")
                TTOonline.text = "🌐 " + get12hFormat(value.child("trashOpenTimeHr").getValue().toString().toInt(), value.child("trashOpenTimeMi").getValue().toString().toInt())
                TTConline.text = "\uD83C\uDF10 " + get12hFormat(value.child("trashCloseTimeHr").getValue().toString().toInt(), value.child("trashCloseTimeMi").getValue().toString().toInt())
                FTOonline.text = "\uD83C\uDF10 " + get12hFormat(value.child("freshOpenTimeHr").getValue().toString().toInt(), value.child("freshOpenTimeMi").getValue().toString().toInt())
                FTConline.text = "\uD83C\uDF10 " + get12hFormat(value.child("freshCloseTimeHr").getValue().toString().toInt(), value.child("freshCloseTimeMi").getValue().toString().toInt())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }

        })

        updateLables()

        button.setOnClickListener {
            updateFirebaseTimeRef()
        }

        trashTimeOpen.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { view, hourOfDay, minute ->
                tsTV.setText("🟢 " + get12hFormat(hourOfDay, minute))
//                tsTV.visibility = View.VISIBLE
                trashOpenTimeHr = hourOfDay
                trashOpenTimeMi = minute
                TObool = true
            }, hour, minute, false)
            timePickerDialog.show()
        }

        trashTimeClose.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { view, hourOfDay, minute ->
                teTV.setText("🟢 " + get12hFormat(hourOfDay, minute))
//                teTV.visibility = View.VISIBLE
                trashCloseTimeHr = hourOfDay
                trashCloseTimeMi = minute
                TCbool = true
            }, hour, minute, false)
            timePickerDialog.show()
        }

        freshTimeOpen.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { view, hourOfDay, minute ->
                fsTV.setText("🟢 " + get12hFormat(hourOfDay, minute))
//                fsTV.visibility = View.VISIBLE
                freshOpenTimeHr = hourOfDay
                freshOpenTimeMi = minute
                FObool = true
            }, hour, minute, false)
            timePickerDialog.show()
        }

        freshTimeClose.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { view, hourOfDay, minute ->
                feTV.setText("🟢 " + get12hFormat(hourOfDay, minute))
//                feTV.visibility = View.VISIBLE
                freshCloseTimeHr = hourOfDay
                freshCloseTimeMi = minute
                FCbool = true
            }, hour, minute, false)
            timePickerDialog.show()
        }

    }

    fun updateLables() {
        tsTV.text = "🔴 Trash Start"
        teTV.text = "🔴 Trash End"
        fsTV.text = "🔴 Fresh Start"
        feTV.text = "🔴 Fresh End"
    }

    fun get12hFormat(hr: Int, min: Int): String {
        var hour = hr
        var am_pm = "AM"
        if (hour > 12) {
            hour -= 12
            am_pm = "PM"
        }
        //add leading zero if needed
        var minStr = min.toString()
        if (min < 10) {
            minStr = "0$min"
        }
        var hourStr = hour.toString()
        if (hour < 10) {
            hourStr = "0$hour"
        }
        return "$hourStr:$minStr $am_pm"
    }


    fun updateFirebaseTimeRef() {

//        freshCloseTimeHr = freshTimeClose.text.toString().toInt()
//        freshCloseTimeMi = freshTimeClose.text.toString().toInt()
//        freshOpenTimeHr = freshTimeOpen.text.toString().toInt()
//        freshOpenTimeMi = freshTimeOpen.text.toString().toInt()
//
//        trashCloseTimeHr = trashTimeClose.text.toString().toInt()
//        trashCloseTimeMi = trashTimeClose.text.toString().toInt()
//        trashOpenTimeHr = trashTimeOpen.text.toString().toInt()
//        trashOpenTimeMi = trashTimeOpen.text.toString().toInt()
//
//        Toast.makeText(this, trashOpenTimeHr, Toast.LENGTH_SHORT).show()

        if(!TObool || !TCbool || !FObool || !FCbool) {
            Toast.makeText(this, "Please set all the time", Toast.LENGTH_SHORT).show()
            return
        }

        myRef.child("test/freshCloseTimeHr").setValue(freshCloseTimeHr)
        myRef.child("test/freshCloseTimeMi").setValue(freshCloseTimeMi)
        myRef.child("test/freshOpenTimeHr").setValue(freshOpenTimeHr)
        myRef.child("test/freshOpenTimeMi").setValue(freshOpenTimeMi)

        myRef.child("test/trashCloseTimeHr").setValue(trashCloseTimeHr)
        myRef.child("test/trashCloseTimeMi").setValue(trashCloseTimeMi)
        myRef.child("test/trashOpenTimeHr").setValue(trashOpenTimeHr)
        myRef.child("test/trashOpenTimeMi").setValue(trashOpenTimeMi)
        Toast.makeText(this, "Ho gaya set", Toast.LENGTH_SHORT).show()
    }
}