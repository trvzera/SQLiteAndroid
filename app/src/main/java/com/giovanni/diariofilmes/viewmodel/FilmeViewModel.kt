package com.giovanni.diariofilmes.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.giovanni.diariofilmes.database.DatabaseHelper
import com.giovanni.diariofilmes.model.Filme
import com.giovanni.diariofilmes.repository.FilmeRepository
import kotlinx.coroutines.launch

sealed interface FilmesUiState {
    object Loading : FilmesUiState
    data class Success(val filmes: List<Filme>) : FilmesUiState
    data class Error(val message: String) : FilmesUiState
}

class FilmesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FilmeRepository(DatabaseHelper(application))

    var uiState: FilmesUiState by mutableStateOf(FilmesUiState.Loading)
        private set

    init { carregarFilmes() }

    fun carregarFilmes() {
        viewModelScope.launch {
            uiState = FilmesUiState.Loading
            uiState = try {
                FilmesUiState.Success(repository.listarTodos())
            } catch (e: Exception) {
                FilmesUiState.Error(e.message ?: "Erro ao acessar o banco de dados.")
            }
        }
    }

    fun salvarFilme(titulo: String, genero: String, ano: Int) {
        viewModelScope.launch {
            repository.inserir(Filme(titulo = titulo, genero = genero, ano = ano))
            carregarFilmes()
        }
    }
}