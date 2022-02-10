package com.example.bookhiltflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookhiltflow.model.Repositorio
import com.example.bookhiltflow.model.models.Books
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class VistaModeloOriginal @Inject constructor(private val repo: Repositorio) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState


    init {
        viewModelScope.launch(Dispatchers.IO) {

            repo.fetchListApiFlow()
                .catch {
                    repo.selectAllBooks().map {listBookDb->
                        listBookDb.filter { books -> books.favorite ==1   }
                        _uiState.value = UiState.Success(listBookDb)
                    }
                }
                .onEach { bookList -> repo.insertBooks(bookList) }
                .collect {bookList->
                    _uiState.value = UiState.Success(bookList)
                }


        }
    }

    fun updateBook(book: Books) {
        viewModelScope.launch(Dispatchers.IO){
            repo.updateBook(book)
        }
    }


}
/*

sealed class UiState {
    data class Success(val listado: List<Books>) : UiState()
    data class Error(val message: String) : UiState()
    object Loading : UiState()

}*/