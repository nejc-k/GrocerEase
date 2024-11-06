package com.prvavaja.grocerease

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.prvavaja.grocerease.databinding.ActivityMapAddBinding
import com.prvavaja.grocerease.databinding.ActivityMapBinding
import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

class MapAdd : AppCompatActivity() {
    private lateinit var binding: ActivityMapAddBinding //ADD THIS LINE
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMapAddBinding.inflate(layoutInflater) //ADD THIS LINE
        setContentView(binding.root)

        val store = intent.getStringExtra("STORE")
        val storeName = intent.getStringExtra("STORE_NAME")

        binding.textViewStore.text=store
        binding.textViewStoreName.text=storeName
        val originalText = store.toString()
        val lowerCaseStore = originalText.lowercase()
        //val selectedStore = "store1" // This can be dynamic based on user input or selection

        val itemList = loadItemsFromJson(this, lowerCaseStore)

        // Now itemList contains the Item objects loaded from the JSON file
        itemList.forEach {
            println("Item Name: ${it.itemName}, Amount: ${it.amaut}, Store: ${it.store}")
        }

        binding.buttonMapAddBack.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
    }

    fun loadItemsFromJson(context: Context, jsonFileName: String): List<Item> {
        val itemList = mutableListOf<Item>()

        // Get the resource ID of the JSON file
        val resourceId = context.resources.getIdentifier(jsonFileName, "raw", context.packageName)

        // Read the JSON file
        val inputStream = context.resources.openRawResource(resourceId)
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        // Parse JSON data
        val jsonObject = JSONObject(jsonString)
        val itemsArray: JSONArray = jsonObject.getJSONArray("items")

        for (i in 0 until itemsArray.length()) {
            val itemObject = itemsArray.getJSONObject(i)
            val itemName = itemObject.getString("itemName")
            val amount = itemObject.getString("amount")

            // Create Item object and add it to the list
            val item = Item(itemName, jsonFileName, amount)
            itemList.add(item)
        }

        return itemList
    }
}