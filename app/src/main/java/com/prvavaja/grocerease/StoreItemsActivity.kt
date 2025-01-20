package com.prvavaja.grocerease

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.osmdroid.util.GeoPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StoreItemsActivity : AppCompatActivity() {
    lateinit var app: MyApplication
    private lateinit var apiService: ApiService
    private lateinit var storeName: String
    private lateinit var shortStoreName: String
    private var currentSearch = ""
    private var categoryFilter = "No Category Filter"
    private lateinit var adapter: StoreItemsAdapter
    private val itemList = mutableListOf<BackendItem>()
    private val itemExample = BackendItem(_id =  "12345","Title",
        10.0,"SomeURL",10.0,"Catasda","Mercator",
        "10.12.2024", "20.10.2025", __v = 10
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        storeName = intent.getStringExtra("STORE_NAME").toString()
        shortStoreName = intent.getStringExtra("STORE").toString()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_items)
        val singleListTitleTV: TextView = this.findViewById(R.id.storeListTitleTV)
        val listToAddTo: TextView = this.findViewById(R.id.listToAddToTV)
        val searchView: SearchView = this.findViewById(R.id.itemsSearchView)
        singleListTitleTV.text = storeName
        app = application as MyApplication
        listToAddTo.text = "Current List: " + app.currentList.listName
        // Initialize RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.itemsStoreRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StoreItemsAdapter(itemList, ::handleAddItem)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                currentSearch = query.toString()
                postRequest()
                val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                currentFocus?.let {
                    inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        val categorySpinner = findViewById<Spinner>(R.id.categorySpinner)

// Set up an adapter for the Spinner (if not using static entries in XML)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.categories,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCategory = parent.getItemAtPosition(position).toString()
                categoryFilter = selectedCategory
                postRequest()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        postRequest()
    }

    private fun postRequest(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://grocerease.ddns.net/api/")
            .addConverterFactory(GsonConverterFactory.create())  // Use Gson for parsing
            .build()
        apiService = retrofit.create(ApiService::class.java)


        //now just add all the restrictions here and this is it
        val json = JSONObject()
        json.put("store",shortStoreName)

        if(categoryFilter != "No Category Filter"){
            json.put("category",categoryFilter)
        }

        if(currentSearch != ""){
            json.put("title",currentSearch)
        }

        val requestBody = json.toString().toRequestBody("application/json; charset=utf-8".toMediaType())

        // Fetch items
        apiService.postItem(requestBody).enqueue(object : Callback<List<BackendItem>> {
            override fun onResponse(call: Call<List<BackendItem>>, response: Response<List<BackendItem>>) {
                if (response.isSuccessful) {
                    itemList.clear()
                    itemList.addAll(response.body()?.take(40) ?: emptyList())
                    adapter.updateList(itemList)
                }
                else {
                    Log.e("StoreItemsActivity", "API call failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<BackendItem>>, t: Throwable) {
                Log.e("StoreItemsActivity", "Error fetching items from API", t)
            }
        })
    }

    private fun filterItems(query: String?) {
        val filteredList = itemList.filter {
            it.title.contains(query ?: "", ignoreCase = true)
        }
        adapter.updateList(filteredList)
    }

    private fun handleAddItem(item: BackendItem) {
        app.currentList.addItem(Item(item.title, item.store, "1"))
        Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show()
    }

    fun StorebackOnClick(view: View) {
        val intent = Intent(this, ListsActivity::class.java)
        intent.putExtra("STORE_NAME", storeName)
        intent.putExtra("STORE", shortStoreName)
        startActivity(intent)
        finish()
    }


}
