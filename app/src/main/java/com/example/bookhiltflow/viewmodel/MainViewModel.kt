package com.example.bookhiltflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookhiltflow.model.Repositorio
import com.example.bookhiltflow.model.models.BookDetail
import com.example.bookhiltflow.model.models.Books
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: Repositorio) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _uiStateDetail = MutableStateFlow<UiStateDetail>(UiStateDetail.Loading)
    val uiStateDetail: StateFlow<UiStateDetail> = _uiStateDetail


    init {
        viewModelScope.launch(IO) {
            selectAllBooks()
            //fetchListApiFlow()
        }
    }

    fun updateBook(book: Books) {
        viewModelScope.launch(IO) {
            repo.updateBook(book)
        }
    }

    suspend fun selectAllBooks() {
        repo.selectFlow
            .collect {
                repo.fetchListApiFlow()
                    .onEach { bookList ->
                        repo.insertBooks(bookList)
                    }
                    .collect {
                        _uiState.value = UiState.Success(it)
                    }

            }


    }


    fun selectBookDetaill(id:Int){
        viewModelScope.launch(IO) {
            repo.selectBookDetail(id)
                .collect {
                    repo.fetchBookDetails(id)
                        .onEach { bookDetail ->
                            repo.insertDetailsBook(bookDetail)
                        }
                        .collectLatest {
                            _uiStateDetail.value = UiStateDetail.Success(it)

                        }
                }
        }
    }

    fun insertBookDetail(id: Int) {
        viewModelScope.launch(IO) {
            repo.fetchBookDetails(id).collect {
                repo.insertDetailsBook(it)
            }

        }
    }

    suspend fun fetchListApiFlow() {
        repo.fetchListApiFlow()
            .catch {
                repo.selectAllBooks()
                    .collect { listBookDb ->
                        _uiState.value = UiState.Success(listBookDb)
                    }
            }
            .onEach { bookList -> repo.insertBooks(bookList) }
            .collect { bookList ->
                _uiState.value = UiState.Success(bookList)
            }
    }

}

sealed class UiState {
    data class Success(val listado: List<Books>) : UiState()
    data class Error(val message: String) : UiState()
    object Loading : UiState()

}

sealed class UiStateDetail {
    data class Success(val bookDetail: BookDetail) : UiStateDetail()
    data class Error(val message: String) : UiStateDetail()
    object Loading : UiStateDetail()
}

/*
    fun selectBookDetaill(id: Int) {
        viewModelScope.launch(IO) {
            repo.selectBookDetail(id)
                .collect {
                    //repo.fetchBookDetails(id)
                    _uiStateDetail.value = UiStateDetail.Success(it)
                }

        }
    }
 */
/*
    fun selectBookDetaill(id:Int){
        viewModelScope.launch(IO) {
            repo.selectBookDetail(id)
                .collect {
                    repo.fetchBookDetails(id)
                        .onEach { bookDetail ->
                            repo.insertDetailsBook(bookDetail)
                        }
                        .collectLatest {
                            _uiStateDetail.value = UiStateDetail.Success(it)

                        }
            }
        }
    }
 */
/*
   suspend fun selectAllBooks() {
        repo.selectAllBooks()
            .catch {
                repo.fetchListApiFlow()
                    .onEach { bookList ->
                        repo.insertBooks(bookList)
                    }

            }
            .collect { bookList ->
                _uiState.value = UiState.Success(bookList)
            }
    }
 */

/*
    suspend fun selectAllBooks() {
        repo.selectFlow

            .map { bookList ->
                if (bookList[0].title != ""){
                    _uiState.value = UiState.Success(bookList)
                }else{
                    fetchListApiFlow()
                }

            }
            .catch {
                repo.fetchListApiFlow()
                    .onEach { bookList ->
                        repo.insertBooks(bookList)
                    }

            }

    }
 */

/*+
@HiltViewModel
class MainViewModel @Inject constructor(private val repo: Repositorio) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState


    init {
        viewModelScope.launch(IO) {
            repo.fetchListApiFlow()
                .catch { e -> UiState.Error(e.message.toString()) }
                .collect {
                    _uiState.value = UiState.Success(it)
                }

        }
    }


}

sealed class UiState {
    data class Success(val listado: List<Books>) : UiState()
    data class Error(val message: String) : UiState()
    object Loading : UiState()

}
 */

/*
            repo.fetchListApiFlow()
                .catch {
                    repo.selectAllBooks().map { listBookDb ->
                        listBookDb.filter { books -> books.favorite == 1 }
                        _uiState.value = UiState.Success(listBookDb)
                    }
                }
                .onEach { bookList -> repo.insertBooks(bookList) }
                .collect { bookList ->
                    _uiState.value = UiState.Success(bookList)
                }
 */

/*
   repo.selectAllBooks()
                .catch { e->
                    repo.fetchListApiFlow().collect {booksApi->
                        repo.insertBooks(booksApi)
                    }

                }
                .collect { booksDb->
                    _uiState.value = UiState.Success(booksDb)
                }


 */