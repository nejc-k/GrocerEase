package com.prvavaja.grocerease

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
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

class ItemAdapter(private val itemList: List<Item>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private val priceVisibilityList: MutableList<Boolean> = MutableList(itemList.size) { false }

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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.titleTextView.text = currentItem.itemName
        holder.descriptionTextView.text = "Number of items: " + currentItem.amount
        holder.oldPriceTextView.text = currentItem.oldPrice.toString()
        holder.newPriceTextView.text = currentItem.newPrice.toString()
        holder.defaultPriceTextView.text = currentItem.newPrice.toString()

        if (priceVisibilityList[position]) {
            if (String.format("%.2f", itemList[position].newPrice).toDouble() <String.format("%.2f", itemList[position].oldPrice).toDouble()  ) {
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

    override fun getItemCount() = itemList.size

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
    private lateinit var itemList: List<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare_list)

        itemList = listOf(
            Item(
                "Pšenični polbeli kruh, rezan, 1kg",
                "Description 1",
                "3",
                oldPrice = 1.34,
                newPrice = 1.19
            ),
            Item(
                "Baguetta bela, 250g",
                "Description 2",
                "2",
                oldPrice = 0.44,
                newPrice = 0.44
            ),
            Item("Puding vanilija s smetano, 200g", "", "2", oldPrice = 0.34, newPrice = 0.41),
            Item("Blejska klobasa, 400g", "", "1", oldPrice = 2.55, newPrice = 2.64),
            Item("Instant kava 3v1,  10/1, 180g", "", "2", oldPrice = 1.19, newPrice = 1.03),
            Item("100% sok jabolko, 1l", "", "2", oldPrice = 1.56, newPrice = 1.69),
            Item("Nektar jabolko, 1l", "", "2", oldPrice = 0.86, newPrice = 0.79),
            Item("Gazirana naravna mineralna voda primaqua, spar, 1,5l", "", "1", oldPrice = 0.49, newPrice = 0.49),
        )

        recyclerView = findViewById(R.id.recyclerViewComparePrices)
        recyclerView.layoutManager = LinearLayoutManager(this)
        itemAdapter = ItemAdapter(itemList)
        recyclerView.adapter = itemAdapter

        val comparePricesButton = findViewById<Button>(R.id.btn_compare_prices)
        val saveMoneyCard = findViewById<CardView>(R.id.save_money_card)
        val saveMoneyTextView = findViewById<TextView>(R.id.save_money_text)

        var number = 0.0;
        for (item in itemList) {
            val diffPrice = item.oldPrice - item.newPrice;
            if (diffPrice > 0.0)
                number += diffPrice * item.amount.toInt()
        }
        val number2digits: Double = String.format("%.2f", number).toDouble()
        if (number > 0) {
            val store = "Lidl"
            val message =
                "Save <b>$number2digits</b>€ by buying these items in <b>$store</b>."
            saveMoneyTextView.text = HtmlCompat.fromHtml(message, HtmlCompat.FROM_HTML_MODE_LEGACY)

        }

        comparePricesButton.setOnClickListener {
            if (saveMoneyCard.visibility == View.GONE) {
                saveMoneyCard.visibility = View.VISIBLE
            } else {
                saveMoneyCard.visibility = View.GONE
            }

            itemAdapter.togglePriceVisibility()
        }
    }
}
