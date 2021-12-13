package com.example.task5_2.api

import com.example.task5_2.model.Books
import retrofit2.http.GET

interface ApiInterface {
    @GET("/books")
    suspend fun getBooks(): List<Books>

}