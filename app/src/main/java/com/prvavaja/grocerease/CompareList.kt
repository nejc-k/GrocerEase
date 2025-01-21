package com.prvavaja.grocerease

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class ItemAdapter(private val itemList: MutableList<BackendItem>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var priceVisibilityList: MutableList<Boolean> = MutableList(itemList.size) { false }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val oldPriceTextView: TextView = itemView.findViewById(R.id.oldPriceTextView)
        val defaultPriceTextView: TextView = itemView.findViewById(R.id.defaultPriceTextView)
        val newPriceTextView: TextView = itemView.findViewById(R.id.newPriceTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_compare_prices, parent, false)
        return ItemViewHolder(itemView)
    }

    fun updateItemList(newItems: List<BackendItem>) {
        itemList.clear()  // Clear the old list
        itemList.addAll(newItems)  // Add the new items
        priceVisibilityList = MutableList(itemList.size){false}
        togglePriceVisibility()
        notifyDataSetChanged()  // Notify that the data has changed
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        Log.d("ItemAdapter", "Binding before checking if empty")
        if (itemList.isNotEmpty()){
            val currentItem = itemList[position]
            holder.titleTextView.text = currentItem.title
            Log.d("ItemAdapter", "Binding item: ${currentItem.title}")
            holder.descriptionTextView.text = "Number of items: " + currentItem.amount
            holder.oldPriceTextView.text = String.format("%.2f", currentItem.oldPrice)
            holder.newPriceTextView.text = String.format("%.2f", currentItem.newPrice)
            holder.defaultPriceTextView.text = String.format("%.2f", currentItem.newPrice)

            if (priceVisibilityList.isNotEmpty() && priceVisibilityList[position]) {

//                if .toDouble() <String.format("%.2f", itemList[position].oldPrice).toDouble()  ) {
                  if (itemList[position].newPrice!! < itemList[position].oldPrice!!) {
                    holder.newPriceTextView.visibility = View.VISIBLE
                    holder.oldPriceTextView.visibility = View.VISIBLE
                    holder.oldPriceTextView.paintFlags =
                        holder.oldPriceTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    holder.oldPriceTextView.visibility = View.GONE
                    holder.newPriceTextView.visibility = View.GONE
                    holder.defaultPriceTextView.visibility = View.VISIBLE
                }
            } else {
                holder.oldPriceTextView.visibility = View.GONE
                holder.newPriceTextView.visibility = View.GONE
            }
        }
    }

    override fun getItemCount() :Int{
        Log.d("ItemCount",itemList.size.toString() )
        return itemList.size
    }

    fun togglePriceVisibility() {
        for (i in priceVisibilityList.indices) {
            priceVisibilityList[i] = true
        }
        notifyDataSetChanged()
    }
}

class CompareList : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var itemList: MutableList<BackendItem>

    lateinit var app: MyApplication
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare_list)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

        app = application as MyApplication


        recyclerView = findViewById(R.id.recyclerViewComparePrices)
        recyclerView.layoutManager = LinearLayoutManager(this)

        itemList = mutableListOf()
        itemAdapter = ItemAdapter(itemList)
        recyclerView.adapter = itemAdapter
        comparePrices()

    }
    private fun comparePrices() {
        val itemsMap = mapOf("items" to app.currentList.items)
        val jsonString = Json.encodeToString(itemsMap)

        val contentType = "application/json".toMediaTypeOrNull()
        val requestBody = jsonString.toRequestBody(contentType)

        apiService.comparePrices(requestBody).enqueue(object : Callback<ApiResponseCompareItems> {
            override fun onResponse(
                call: Call<ApiResponseCompareItems>,
                response: Response<ApiResponseCompareItems>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    val store = apiResponse?.store
                    val total = apiResponse?.total
                    val items = apiResponse?.items

                    if (!items.isNullOrEmpty()) {
//                        itemList = items
                        itemAdapter.updateItemList(items)
                        Log.d("ITEMS CAST", itemList.toString())
//                        itemAdapter.notifyDataSetChanged()
                    }
                    val saveMoneyCard = findViewById<CardView>(R.id.save_money_card)
                    val saveMoneyTextView = findViewById<TextView>(R.id.save_money_text)

                    var number = 0.0;
                    if (items != null && items.isNotEmpty()) {
                        for (item in itemList) {

                            if (item.oldPrice != null && item.newPrice != null){
                                val diffPrice = item.oldPrice - item.newPrice;
                                if (diffPrice > 0.0)
                                    if (item.amount == null)  {
                                        number += diffPrice
                                    }
                                    else{
                                       number += diffPrice*item.amount
                                    }
                            }
                        }
                        itemAdapter.togglePriceVisibility()
                    }
                    val number2digits  = String.format("%.2f", number)
                    if (number > 0) {
                        val message =
                            "Save <b>$number2digits</b>€ by buying these items in <b>$store</b>."
                        saveMoneyTextView.text = HtmlCompat.fromHtml(message, HtmlCompat.FROM_HTML_MODE_LEGACY)
                        saveMoneyCard.visibility = View.VISIBLE
                    }


                    Log.e("RESPONSE", store.toString())
                    Log.e("ITEMS", itemList.toString())

                } else {
                    println("Failed to fetch items: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ApiResponseCompareItems>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }
        })
    }

}
