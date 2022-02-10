package com.example.bookhiltflow.model

import com.example.bookhiltflow.model.db.BookDao
import com.example.bookhiltflow.model.models.BookDetail
import com.example.bookhiltflow.model.models.Books
import com.example.bookhiltflow.model.remote.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repositorio @Inject constructor(private val api: ApiService, private val dao: BookDao) {

    suspend fun fetchListApiFlow() = flow {
       emit( api.fetchBooksListApi())
    }

    suspend fun fetchBookDetails(id: Int) = flow {
        emit(api.fetchDetails(id))
    }

    fun insertBooks(list: List<Books>) = dao.insertBooks(list)
    fun updateBook(books: Books) = dao.updateBookFav(books)

    fun selectAllBooks(): Flow<List<Books>> = flow {
        while (true){
            emit(dao.selectAllBooks())
        }
    }

    val selectFlow = dao.selectAllBooksFL()

    fun insertDetailsBook(bookDetail: BookDetail) = dao.insertDetailBook(bookDetail)


    fun selectBookDetail(id:Int) = dao.selectBookDetailFlow(id)


}

/*
    suspend fun fetchListApiRes(): Resource.Success<Resource<List<Books>>> {
        val response = try {
            api.fetchBooksListApiResouce()
        } catch (e: Exception) {
            Resource.Error("Hubo un erro")
        }
        return Resource.Success(response)
    }

    suspend fun fetchListApi() = api.fetchBooksListApi()
 */