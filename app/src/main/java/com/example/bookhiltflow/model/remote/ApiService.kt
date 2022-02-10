package com.example.bookhiltflow.model.remote

import com.example.bookhiltflow.model.Resource
import com.example.bookhiltflow.model.models.BookDetail
import com.example.bookhiltflow.model.models.Books
import retrofit2.http.GET
import retrofit2.http.Path

//https://my-json-server.typicode.com/Himuravidal/anchorBooks/books
interface ApiService {
    @GET("books")
    suspend fun fetchBooksListApi(): List<Books>

    @GET("bookDetail/{id}")
    suspend fun fetchDetails(
        @Path(value = "id") id: Int
    ) : BookDetail


    @GET("books")
    suspend fun fetchBooksListApiResouce(): Resource<List<Books>>
}