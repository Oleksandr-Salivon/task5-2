package com.example.task4_4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.task4_4.model.Books
import com.squareup.picasso.Picasso

class RecyclerAdapter (books:List<Books>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    private var myList = books
    private var titles = arrayOf("s","d")
    private var authors = arrayOf("ssss","dsss")
    private var years = arrayOf("ssss","dsss")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = "Title: ${myList[position].title.toString()}"
        holder.itemAuthor.text = "Author: ${myList[position].author.toString()}"
        holder.itemYear.text = "Year\n\n${myList[position].year.toString()}"
    }

    override fun getItemCount(): Int {
        return myList.size
    }
fun setData(newList: List<Books>){
    myList = newList
    notifyDataSetChanged()
}
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemYear: TextView
        var itemTitle: TextView
        var itemAuthor: TextView

        init {
            itemYear = itemView.findViewById(R.id.item_year)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemAuthor = itemView.findViewById(R.id.item_author)
        }
    }
}