package com.example.task4_4

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        var year = "-10000"
        var author = ""
        val itemNoFound = findViewById<TextView>(R.id.itemNoFound)
        val itemFoundCount = findViewById<TextView>(R.id.itemFoundCount)
        itemFoundCount.text = getString(R.string.found_1_d, 0)


        /* hide soft keyboard after writing and sending message or any */

        getBooks.setOnClickListener {


            closeSoftKeyboard(this, getAuthor)
            closeSoftKeyboard(this, getYear)
            CoroutineScope(Dispatchers.IO).launch {
                val decodedJsonResult = api.getBooks()
                val books = decodedJsonResult.toList()
                if (getYear.editText?.text.toString() != "") {
                    year = getYear.editText?.text.toString()
                }
                if (getAuthor.editText?.text.toString() != "") {

                    author = getAuthor.editText?.text.toString()
                }
                val filteredBooks = books.filter { it.year!! >= year.toInt() }
                    .filter { it.author!!.contains("${author}", true) }
                withContext(Dispatchers.Main) {

                    if (filteredBooks.isNotEmpty()) {
                        adapter = RecyclerAdapter(filteredBooks)
                        recyclerView.adapter = adapter
                        itemNoFound.text = ""
                        itemFoundCount.text = getString(R.string.found_1_d, filteredBooks.size)
                    } else {
                        adapter = RecyclerAdapter(filteredBooks)
                        recyclerView.adapter = adapter
                        itemNoFound.text = "Nothing found. Try other options"
                        itemFoundCount.text = getString(R.string.found_1_d, 0)
                    }

                }
            }

        }

    }

    private fun closeSoftKeyboard(context: Context, v: View) {
        val iMm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        iMm.hideSoftInputFromWindow(v.windowToken, 0)
        v.clearFocus()
    }
}