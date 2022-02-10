package com.example.bookhiltflow.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bookhiltflow.model.models.BookDetail
import com.example.bookhiltflow.model.models.Books

@Database(entities = [Books::class, BookDetail::class], version = 1, exportSchema = false)
abstract class BooksDatabase: RoomDatabase() {
    abstract fun dao(): BookDao
}
