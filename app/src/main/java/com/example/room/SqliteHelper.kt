package com.example.room

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper(context : Context, name : String, version : Int) :
    SQLiteOpenHelper(context,name,null,version){
    override fun onCreate(p0: SQLiteDatabase?) {
        val create = "create table memo(" +
                "num integer primary key," +
                "content text," +
                "datetime integer" +
                ")"
        p0?.execSQL(create)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun insertMemo(memo : Memo) {
        val values = ContentValues()
        values.put("content", memo.content)
        values.put("datetime", memo.datetime)

        val wd = writableDatabase
        wd.insert("memo",null,values)
        wd.close()
    }

    fun selectMemo() : MutableList<Memo> {
        val list = mutableListOf<Memo>()

        val select = "select * from memo"
        val rd = readableDatabase
        val cursor = rd.rawQuery(select, null)

        while (cursor.moveToNext()) {
            val numIdx = cursor.getColumnIndex("num")
            val contentIdx = cursor.getColumnIndex("content")
            val dateIdx = cursor.getColumnIndex("datetime")

            val num = cursor.getLong(numIdx)
            val content = cursor.getString(contentIdx)
            val datetime = cursor.getLong(dateIdx)

            list.add(Memo(num, content, datetime))
        }

        cursor.close()
        rd.close()

        return list
    }

    fun updateMemo(memo : Memo) {
        val values = ContentValues()
        values.put("content", memo.content)
        values.put("datetime", memo.datetime)

        val wd = writableDatabase
        wd.update("memo",values,"num = ${memo.num}", null)
        wd.close()
    }

    fun deleteMemo(memo : Memo) {
        val delete = "delete from memo where num = ${memo.num}"
        val db = writableDatabase
        db.execSQL(delete)
        db.close()
    }
}