package com.giovanni.diariofilmes.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import kotlinx.coroutines.flow.Flow;

@Dao
interface FilmeDao {

    @Query("SELECT * FROM filmes ORDER BY ano DESC")
    fun observeAll(): Flow<List<FilmeEntity>>

    @Insert
    suspend fun insertAll(filmes: List<FilmeEntity>);
}