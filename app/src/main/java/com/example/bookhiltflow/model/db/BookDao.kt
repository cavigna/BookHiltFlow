package com.example.bookhiltflow.model.db

import androidx.lifecycle.LiveData
import androidx.room.*

import com.example.bookhiltflow.model.models.BookDetail
import com.example.bookhiltflow.model.models.Books
import kotlinx.coroutines.flow.Flow


@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBooks(books: List<Books>)

    @Query("SELECT * FROM books_table")
    fun selectAllBooks(): List<Books>

    @Update
    fun updateBookFav(books: Books)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailBook(book: BookDetail)

    @Query("SELECT * FROM book_details_table WHERE id=:id")
    fun selectBookDetail(id:Int): LiveData<BookDetail>

    @Query("SELECT * FROM book_details_table WHERE id=:id")
    fun selectBookDetailFlow(id:Int): Flow<BookDetail>

    @Query("SELECT * FROM books_table")
    fun selectAllBooksFL(): Flow<List<Books>>


}