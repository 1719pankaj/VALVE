package com.example.valve

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
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

    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        database = FirebaseDatabase.getInstance("https://espautovalve-default-rtdb.asia-southeast1.firebasedatabase.app")
        myRef = database.reference

        updateLables()

        button.setOnClickListener {
            updateFirebaseTimeRef()
        }

        trashTimeOpen.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { view, hourOfDay, minute ->
                tsTV.setText("$hourOfDay:$minute")
//                tsTV.visibility = View.VISIBLE
                trashOpenTimeHr = hourOfDay
                trashOpenTimeMi = minute
            }, hour, minute, false)
            timePickerDialog.show()
        }

        trashTimeClose.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { view, hourOfDay, minute ->
                teTV.setText("$hourOfDay:$minute")
//                teTV.visibility = View.VISIBLE
                trashCloseTimeHr = hourOfDay
                trashCloseTimeMi = minute
            }, hour, minute, false)
            timePickerDialog.show()
        }

        freshTimeOpen.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { view, hourOfDay, minute ->
                fsTV.setText("$hourOfDay:$minute")
//                fsTV.visibility = View.VISIBLE
                freshOpenTimeHr = hourOfDay
                freshOpenTimeMi = minute
            }, hour, minute, false)
            timePickerDialog.show()
        }

        freshTimeClose.setOnClickListener {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { view, hourOfDay, minute ->
                feTV.setText("$hourOfDay:$minute")
//                feTV.visibility = View.VISIBLE
                freshCloseTimeHr = hourOfDay
                freshCloseTimeMi = minute
            }, hour, minute, false)
            timePickerDialog.show()
        }

    }

    fun updateLables() {
        tsTV.text = "Trash Start"
        teTV.text = "Trash End"
        fsTV.text = "Fresh Start"
        feTV.text = "Fresh End"
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