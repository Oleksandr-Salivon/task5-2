package com.example.task5_2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [
        Author::class,
        Book::class
    ], version = 2, exportSchema = true
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun bookDao(): BooksDao

    companion object{
        @Volatile
        private var INSTANCE: ApplicationDatabase? = null

        fun getInstance(context: Context): ApplicationDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationDatabase::class.java,
                    "DatBase"
                ).build().also {
                    INSTANCE = it
                }

            }
        }
    }

}