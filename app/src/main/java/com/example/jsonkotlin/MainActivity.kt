package com.example.jsonkotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var items: List<Item>
    private lateinit var adapter: ItemAdapter
    lateinit var recyclerView:RecyclerView
    private lateinit var searchButton :Button
    private lateinit var searchEditText:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.itemListView)
        searchButton = findViewById(R.id.searchButton)
        searchEditText = findViewById(R.id.searchEditText)

        val json = """[
        {"name": "Item 1", "price": 10},
        {"name": "Item 2", "price": 20},
        {"name": "Item 3", "price": 30},
        {"name": "Item 4", "price": 40},
        {"name": "Item 5", "price": 50}
    ]"""

        val itemType = object : TypeToken<List<Item>>() {}.type
        items = Gson().fromJson(json, itemType)

        adapter = ItemAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        searchButton.setOnClickListener {
            val query = searchEditText.text.toString()
            val searchResults = searchItems(query)
            adapter.setItems(searchResults)
        }
    }


    private fun searchItems(query: String): List<Item> {
        val results = mutableListOf<Item>()
        for (item in items) {
            if (item.name.contains(query, ignoreCase = true)) {
                results.add(item)
            }
        }
        return results
    }
}
