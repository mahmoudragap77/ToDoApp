package com.example.todoapp

import android.app.DownloadManager.COLUMN_ID
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteDataBaseHelper(context:Context):SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {

    companion object{
       private const val DATABASE_NAME="notesapp.db"
       private const val DATABASE_VERSION= 1
       private const val TABLE_NAME="allnotes"
       private const val COLMUN_ID="_id"
       private const val COLMUN_TITLE="title"
       private const val COLMUN_CONTENT="content"

    }

    override fun onCreate(db: SQLiteDatabase?) {
       val createTabelQuery ="CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY , $COLMUN_TITLE TEXT ,$COLMUN_CONTENT TEXT) "
        db?.execSQL(createTabelQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery ="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertNote(note:Note){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLMUN_TITLE,note.title)
            put(COLMUN_CONTENT,note.content)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun getAllNotes() :List<Note>{
        val noteList = mutableListOf<Note>()
        val db =readableDatabase
        val query ="SELECT * FROM $TABLE_NAME"
        val cursor =db.rawQuery(query,null)

        while(cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLMUN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLMUN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLMUN_CONTENT))

            val note =Note(id, title, content)
            noteList.add(note)

        }

        cursor.close()
        db.close()
        return noteList
    }

    fun updateNote(note :Note){
        val db =writableDatabase
        val values = ContentValues().apply {
            put(COLMUN_TITLE,note.title)
            put(COLMUN_CONTENT,note.content)
        }
        val whereClause ="$COLUMN_ID = ?"
        val whereArgs= arrayOf(note.id.toString())
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
    }

    fun getNoteById(noteId : Int) :Note {
        val db =readableDatabase
        val query ="SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId"
        val cursor =db.rawQuery(query,null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLMUN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLMUN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLMUN_CONTENT))

        cursor.close()
        db.close()
        return Note(id, title, content)
    }

    fun deleteNote(noteId : Int){
        val db =writableDatabase
        val whereClause ="$COLUMN_ID = ?"
        val whereArgs= arrayOf(noteId.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()
    }
}