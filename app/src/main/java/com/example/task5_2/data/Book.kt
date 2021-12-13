package com.example.task5_2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book (
    @PrimaryKey
    val id: Int?,
    val author: Int?,
    val authorName: String?,
    val country: String?,
    val imageLink: String?,
    val language: String?,
    val link: String?,
    val pages: Int?,
    val title: String?,
    val year: Int?


        )