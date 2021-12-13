package com.example.task5_2.data

import androidx.room.Embedded
import androidx.room.Relation

data class AuthorWithBooks(
    @Embedded val author: Author,
    @Relation(
        parentColumn = "id",
        entityColumn = "author"
    )
    val books: List<Book>
)

