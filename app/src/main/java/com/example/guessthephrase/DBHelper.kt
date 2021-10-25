package com.example.guessthephrase

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context):SQLiteOpenHelper(context,"detail.db",null,1) {
   var sdb:SQLiteDatabase = writableDatabase
    override fun onCreate(p0: SQLiteDatabase?) {
        if (p0 != null) {
            p0.execSQL("create table phrase (name text)")
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
    fun saveddata(n1:String): Long {
        val cv = ContentValues()
        cv.put("name",n1)
        var status = sdb.insert("phrase",null,cv)
        return status
    }

//    @SuppressLint("Range")
//    fun retriveData(s1:String):String{
//        var c :Cursor = sdb.query("student",null,"name=?", arrayOf(s1),null,null,null)
//        c.moveToFirst()
//        var loc =  c.getString(c.getColumnIndex("location"))
//        return loc
//    }

    @SuppressLint("Range")
    fun retriveData():MutableList<String>{
        var al = mutableListOf<String>()
        var c : Cursor = sdb.query("phrase",null,null, null,null,null,null)
        if (c.moveToFirst()) {
            do {
                var name =   c.getString(c.getColumnIndex("name"))

                al.add(name)
            } while (c.moveToNext());
        }
        return al
    }
}