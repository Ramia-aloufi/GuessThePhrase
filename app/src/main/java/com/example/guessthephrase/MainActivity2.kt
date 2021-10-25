package com.example.guessthephrase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity2 : AppCompatActivity() {
    lateinit var btn: Button
    lateinit var et:EditText
    lateinit var tv:TextView
    lateinit var db:DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        btn = findViewById(R.id.button)
        et = findViewById(R.id.editTextTextPersonName)
        tv = findViewById(R.id.textView)
        db = DBHelper(this)
        var rr = ""

        var aa = db.retriveData()
        for(i in aa) {
            rr += "$i\n"
        }


        btn.setOnClickListener {
            var aa = et.text.toString()
            var status = db.saveddata(aa)
            rr += "$aa\n"
            tv.text = rr
            Toast.makeText(this,"Data Saving $status",Toast.LENGTH_SHORT).show()
            et.text.clear()
        }
        tv.text = rr


    }
}