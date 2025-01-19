package com.prvavaja.grocerease

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class StoreItemsAdapter(private var items: MutableList<BackendItem>,
                        private val onAddButtonClick: (BackendItem) -> Unit)
    : RecyclerView.Adapter<StoreItemsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.itemStoreImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.itemStoreTitleTextView)
        val priceTextView: TextView = itemView.findViewById(R.id.itemStorePriceTextView)
        val discountTextView: TextView = itemView.findViewById(R.id.itemStoreDiscountTextView)
        val categoryTextView: TextView = itemView.findViewById(R.id.itemStoreCategoryTextView)
        val storeTextView: TextView = itemView.findViewById(R.id.itemStoreTextView)
        val addItemButton: Button = itemView.findViewById(R.id.addStoreItemButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.store_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        // Populate the views with item data
        holder.titleTextView.text = item.title
        holder.priceTextView.text = "Price: ${item.price}"
        holder.discountTextView.text = "Discount: ${item.discount}%"
        holder.categoryTextView.text = "Category: ${item.category}"
        holder.storeTextView.text = "Store: ${item.store}"

        // Load the image using Glide
        Glide.with(holder.itemView.context)
            .load(item.imageURL)
            .into(holder.imageView)
        holder.addItemButton.setOnClickListener {
            onAddButtonClick(item)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newList: List<BackendItem>) {
        items = newList.toMutableList()
        notifyDataSetChanged()
    }
}
