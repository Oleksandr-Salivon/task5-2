package com.example.task4_4.api

import com.example.task4_4.model.Books
import retrofit2.http.GET

interface ApiInterface {
    @GET("/books")
    suspend fun getBooks(): List<Books>

}