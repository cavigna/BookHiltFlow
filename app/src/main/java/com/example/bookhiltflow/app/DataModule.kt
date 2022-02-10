package com.example.bookhiltflow.app

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.example.bookhiltflow.model.db.BookDao
import com.example.bookhiltflow.model.db.BooksDatabase
import com.example.bookhiltflow.model.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun providesRetrofitClient(): ApiService{
        return Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/Himuravidal/anchorBooks/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesBookDb(@ApplicationContext context: Context): BooksDatabase =
        databaseBuilder(context, BooksDatabase::class.java, "books.db").build()

    @Singleton
    @Provides
    fun providesDao(db: BooksDatabase) = db.dao()
}