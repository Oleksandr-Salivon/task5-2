package com.example.task5_2.data

import androidx.room.*
import com.example.task5_2.model.Books

@Dao
interface BooksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAuthors(vararg authors: Author)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBooks(vararg books: Book)

//    @Transaction
//    @Query("SELECT * FROM books  WHERE books.year >= :year AND  books.author in(SELECT authorId FROM authors WHERE authorName LIKE :author) ")
//    suspend fun getBooks5(year: Int, author: String): List<Book>

    @Transaction
    @Query("SELECT * FROM books JOIN authors ON books.author=authors.authorId  WHERE books.year >= :year AND  authors.authorName LIKE :author")
    suspend fun getBooks(year: Int, author: String): List<Book>

}
