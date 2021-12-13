package com.example.task5_2

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task5_2.data.ApplicationDatabase
import com.example.task5_2.data.Author
import com.example.task5_2.data.AuthorWithBooks
import com.example.task5_2.data.Book
import com.example.task5_2.model.Books
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layoutManager = LinearLayoutManager(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        val myApplication = application as MyApplication
        val api = myApplication.httpApiService
        val getBooks = findViewById<Button>(R.id.button)
        val getYear = findViewById<TextInputLayout>(R.id.itemInputYear)
        val getAuthor = findViewById<TextInputLayout>(R.id.itemInputAuthor)
        var maskedYear = "-10000"
        var maskedAuthor = ""
        val itemNoFound = findViewById<TextView>(R.id.itemNoFound)
        val itemFoundCount = findViewById<TextView>(R.id.itemFoundCount)
        itemFoundCount.text = getString(R.string.found_1_d, 0, 0)
        val dao = ApplicationDatabase.getInstance(this).bookDao()
        val textViewID1 = findViewById<TextView>(R.id.textViewID1)

        fun dataFromRoom() {
            closeSoftKeyboard(this, getAuthor)
            closeSoftKeyboard(this, getYear)

            CoroutineScope(Dispatchers.IO).launch {
                if (getYear.editText?.text.toString() != "") {
                    maskedYear = getYear.editText?.text.toString()
                }
                if (getAuthor.editText?.text.toString() != "") {

                    maskedAuthor = getAuthor.editText?.text.toString()
                }
//
                val filteredBooks: List<Book> =
//                    dao.getBooks(Integer.parseInt(maskedYear), "%$maskedAuthor%")
                    dao.getBooks(Integer.parseInt(maskedYear),"%$maskedAuthor%")

                withContext(Dispatchers.Main) {
                    textViewID1.text = filteredBooks.size.toString()
                    if (filteredBooks.isNotEmpty()) {
                        adapter = RecyclerAdapter(filteredBooks.take(3))
                        recyclerView.adapter = adapter
                        itemNoFound.text = ""
                        itemFoundCount.text = getString(
                            R.string.found_1_d,
                            filteredBooks.size,
                            filteredBooks.take(3).size
                        )
                    } else {
                        adapter = RecyclerAdapter(filteredBooks)
                        recyclerView.adapter = adapter
                        itemNoFound.text = "Nothing found. Try other options"
                        itemFoundCount.text = getString(R.string.found_1_d, 0, 0)
                    }

                }

            }
        }

        fun getData() {
            closeSoftKeyboard(this, getAuthor)
            closeSoftKeyboard(this, getYear)
            CoroutineScope(Dispatchers.IO).launch {

                val books = api.getBooks().toList()
                var x = 1
                val authors =
                    books.distinctBy { it.author }.map { it -> Author(x++, it.author) }
                dao.insertAllAuthors(*authors.toTypedArray())
                val booksList = mutableListOf<Book>()

                x = 1
                for (item in books) {
                    booksList.add(
                        Book(
                            x++,
                            authors.indexOfFirst { it.authorName == item.author } + 1,
                            item.authorName,
                            item.country,
                            item.imageLink,
                            item.language,
                            item.link,
                            item.pages,
                            item.title,
                            item.year
                        )
                    )
                }
                dao.insertAllBooks(*booksList.toTypedArray())

                withContext(Dispatchers.Main) {
//
                }
            }

        }
        getData()

        getBooks.setOnClickListener {
            dataFromRoom()
        }

    }

    private fun closeSoftKeyboard(context: Context, v: View) {
        val iMm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        iMm.hideSoftInputFromWindow(v.windowToken, 0)
        v.clearFocus()
    }
}