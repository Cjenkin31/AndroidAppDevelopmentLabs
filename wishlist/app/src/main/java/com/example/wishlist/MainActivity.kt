package com.example.wishlist

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: WishlistAdapter
    private lateinit var itemsList: MutableList<WishlistItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemsList = mutableListOf()
        adapter = WishlistAdapter(itemsList)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this) // Set LayoutManager
        recyclerView.adapter = adapter

        val itemName: EditText = findViewById(R.id.itemName)
        val itemPrice: EditText = findViewById(R.id.userPrice)
        val itemUrl: EditText = findViewById(R.id.userUrl)
        val submitButton: Button = findViewById(R.id.submit_button)

        submitButton.setOnClickListener {
            val name = itemName.text.toString().trim()
            val price = itemPrice.text.toString().trim()
            val url = itemUrl.text.toString().trim()

            if (name.isNotEmpty() && price.isNotEmpty() && url.isNotEmpty()) {
                val item = WishlistItem(name, price, url)
                adapter.addItem(item)

                // Clear the input fields
                itemName.text.clear()
                itemPrice.text.clear()
                itemUrl.text.clear()
            }
        }
    }
}
