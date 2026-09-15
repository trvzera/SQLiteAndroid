package com.giovanni.diariofilmes.ui.theme.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.giovanni.diariofilmes.viewmodel.FilmesUiState
import com.giovanni.diariofilmes.viewmodel.FilmesViewModel

@Composable
fun FilmeScreen(viewModel: FilmesViewModel = viewModel()) {
    var titulo by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var ano by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = genero, onValueChange = { genero = it }, label = { Text("Genero") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = ano, onValueChange = { ano = it }, label = { Text("Ano") }, modifier = Modifier.fillMaxWidth())

        Button(
            onClick = {
                val anoInt = ano.toIntOrNull() ?: return@Button
                viewModel.salvarFilme(titulo, genero, anoInt)
                titulo = ""; genero = ""; ano = ""
            },
            modifier = Modifier.padding(top = 12.dp)
        ) { Text("Salvar Filme") }

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = viewModel.uiState) {
            is FilmesUiState.Loading -> CircularProgressIndicator()
            is FilmesUiState.Error -> Text("Erro: ${state.message}")
            is FilmesUiState.Success -> LazyColumn {
                items(state.filmes, key = { it.id }) { livro ->
                    ListItem(
                        headlineContent = { Text(livro.titulo) },
                        supportingContent = { Text("${livro.genero} · ${livro.ano}") }
                    )
                }
            }
        }
    }
}