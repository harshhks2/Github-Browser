package com.example.githubbrowser

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASENAME = "RepoDatabase"
val TABLENAME = "Repositories"
val COL_OWNER = "owner"
val COL_NAME = "name"
val COL_DESC = "description"
val COL_ID = "id"

class DatabaseHelper(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,
        1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE Repositories(id INTEGER PRIMARY KEY AUTOINCREMENT, owner TEXT, name TEXT, description TEXT)")
        //Toast.makeText(context, "Table Created", Toast.LENGTH_SHORT).show()
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //onCreate(db);
    }
    fun insertData(repo: DataRepository) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("owner", repo.owner)
        contentValues.put("name", repo.name)
        contentValues.put("description", repo.description)
        val result = database.insert("Repositories", null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    fun readData(): MutableList<DataRepository> {
        val list: MutableList<DataRepository> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLENAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val repo = DataRepository(result.getString(result.getColumnIndex(COL_OWNER)),result.getString(result.getColumnIndex(COL_NAME)),
                        result.getString(result.getColumnIndex(COL_DESC)))
                list.add(repo)
            }
            while (result.moveToNext())
        }
        return list
    }
    fun deleteData(repo: DataRepository){
        val database = this.writableDatabase
        //database.execSQL("Delete from Repositories where owner = ${repo.owner} && name = ${repo.name}")
        database.delete("Repositories", "owner = ? AND name = ?",arrayOf(repo.owner,repo.name))

    }
}