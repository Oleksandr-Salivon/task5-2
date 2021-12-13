package com.example.task5_2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authors")
data class Author(
    @PrimaryKey
    val authorId : Int?,
    val authorName : String?
)

