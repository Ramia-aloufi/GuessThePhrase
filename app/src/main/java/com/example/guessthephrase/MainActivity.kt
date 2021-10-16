package com.example.guessthephrase

import android.content.DialogInterface
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

lateinit var tv1:TextView
lateinit var tv2:TextView
lateinit var hs:TextView
lateinit var rv:RecyclerView
lateinit var et:EditText
lateinit var btn:Button
var r = 0
var txt = ""
var count = 10
 var state:Int = 0
var convertPhrase = ""
lateinit var al:ArrayList<Items>
var phrase:String = "coding dojo"
lateinit var starphrase:CharArray
lateinit var sp:SharedPreferences
var highscore = 0

data class Items(val textval :String,val color:Int)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeUI()
        btn.setOnClickListener {

            btnClicked()
        }
         check()
        Rvadapter()
    }
    fun check(){
        if (count == 0) {
            getalert("Guessed Letter", "Game Over..\n" + "Phrase is $phrase")

        }
    }

    override fun recreate() {
        super.recreate()
        count = 10
        state = 0
        txt = ""
        et.text.clear()
        et.isFocusable = false
        phrase = ""
    }

    fun initializeUI(){
        tv1 = findViewById(R.id.tv1)
        tv2 = findViewById(R.id.tv2)
        hs = findViewById(R.id.highscore)
        rv = findViewById(R.id.rv)
        et = findViewById(R.id.et)
        btn = findViewById(R.id.btn)
        al = ArrayList()
        sp = this.getSharedPreferences("myscore", MODE_PRIVATE)
        var num = sp.getInt("highscore",0)
        hs.text = "High Score :$num"
        convertPhraseToStar()



    }
    fun convertPhraseToStar(){
        for(i in phrase){
            if (i.isWhitespace()){
                convertPhrase += " "
            }else{
                convertPhrase += "*"
            }
        }
        // userguess = phrase.replace("[^A-Za-z0-9]","*")
        tv1.text = convertPhrase
         starphrase = convertPhrase.toCharArray()
    }
    fun btnClicked() {
            var UserInt = et.text.toString().lowercase()
        if (state == 0) {
            if (UserInt == phrase) {
                getalert("Guessed Phrase", "Welldone..\n" + "Phrase is $phrase")
            } else {
                getalert1("Guessed Phrase", "Wrong..\n" + "Guess a Letter",)
                et.text.clear()
                al.add(Items("Wrong guess : $UserInt",1))
                rv.adapter?.notifyDataSetChanged()
            }
        } else {

            if (phrase == tv1.text.toString()) {
                getalert("Guessed Letter", "Welldone..\n" + "Phrase is $phrase")
                highscore = 10 - count
                var spe = sp.edit()
                spe.putInt("highscore",highscore)
                spe.apply()
            }else{
                convertToLetter(UserInt)
                count --
                al.add(Items("$count guessess remining", 2))
                et.text.clear()

                check()
            }
            et.text.clear()
    }
    }

    fun getalert(title:String, message:String){
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage(message)
                .setPositiveButton("Yes", DialogInterface.OnClickListener {
                        dialog, id -> this.recreate()
                })
                .setNegativeButton("No", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })
            val alert = dialogBuilder.create()
            alert.setTitle(title)
            alert.show()
    }
    fun getalert1(title:String, message:String){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(message)
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id -> guessLetter()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle(title)
        alert.show()
    }

    fun convertToLetter(UserIn: String){

        Log.d("lngth","${phrase[1]}")
        for (i in phrase.indices){
            if(phrase[i].toString() == UserIn ){
                starphrase[i] = phrase[i]
                r ++
            }
            txt += starphrase[i]
        }

        Log.d("lngth","$phrase")
        Log.d("starphrase","$starphrase")
        Log.d("txt","$txt")

        tv1.text = txt
        if (r!==0) {
            al.add(Items(" found $r ${UserIn.capitalize()}(s)", 0))
        }else {
            al.add(Items(" found $r ${UserIn.capitalize()}(s)", 1))
        }
        rv.adapter?.notifyDataSetChanged()
        txt = ""
        r = 0
    }


    fun Rvadapter(){
        rv.adapter = MyAdapter(al)
        rv.layoutManager = LinearLayoutManager(this)
    }

    fun guessLetter(){
        et.hint = "Guess a Letter"
        tv2.text = "Guess a Letter"
        state = 1
    }

}