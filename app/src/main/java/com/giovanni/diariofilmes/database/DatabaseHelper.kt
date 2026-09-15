package com.giovanni.diariofilmes.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_FILMES (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_TITULO TEXT NOT NULL,
                $COL_GENERO TEXT NOT NULL,
                $COL_ANO INTEGER NOT NULL
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FILMES")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "filme.db"
        const val DATABASE_VERSION = 1
        const val TABLE_FILMES = "filmes"
        const val COL_ID = "id"
        const val COL_TITULO = "titulo"
        const val COL_GENERO = "genero"
        const val COL_ANO = "ano"
    }
}