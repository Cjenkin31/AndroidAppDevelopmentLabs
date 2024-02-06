package com.example.wishlist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WishlistAdapter(private val items: MutableList<WishlistItem>) : RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val itemPrice: TextView = itemView.findViewById(R.id.item_price)
        val itemUrl: TextView = itemView.findViewById(R.id.item_url)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.wishlist_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemName.text = item.name
        holder.itemPrice.text = item.price
        holder.itemUrl.text = item.url
    }

    override fun getItemCount() = items.size

    fun addItem(item: WishlistItem) {
        items.add(item)
        Log.d("WishlistAdapter", "Item added: ${item.name}, Total items: ${items.size}")
        notifyItemInserted(items.size - 1)
    }



}
