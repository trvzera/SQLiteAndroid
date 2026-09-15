package com.giovanni.diariofilmes.repository

import android.content.ContentValues
import com.giovanni.diariofilmes.database.DatabaseHelper
import com.giovanni.diariofilmes.model.Filme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FilmeRepository(private val dbHelper: DatabaseHelper) {

    suspend fun inserir(filme: Filme) = withContext(Dispatchers.IO) {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put(DatabaseHelper.COL_TITULO, filme.titulo)
            put(DatabaseHelper.COL_GENERO, filme.genero)
            put(DatabaseHelper.COL_ANO, filme.ano)
        }
        db.insert(DatabaseHelper.TABLE_FILMES, null, valores)
    }

    suspend fun listarTodos(): List<Filme> = withContext(Dispatchers.IO) {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_FILMES,
            null, null, null, null, null,
            "${DatabaseHelper.COL_ANO} DESC"
        )
        val filmes = mutableListOf<Filme>()
        cursor.use {
            while (it.moveToNext()) {
                filmes.add(
                    Filme(
                        id = it.getLong(it.getColumnIndexOrThrow(DatabaseHelper.COL_ID)),
                        titulo = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COL_TITULO)),
                        genero = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COL_GENERO)),
                        ano = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.COL_ANO))
                    )
                )
            }
        }
        filmes
    }
}