package com.prvavaja.grocerease

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SingleListActivity : AppCompatActivity() {
    lateinit var app: MyApplication
    lateinit var myAdapter: MyAdapterItems

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_list)

        app = application as MyApplication
        myAdapter = MyAdapterItems(app)
        val recyclerView: RecyclerView = this.findViewById(R.id.itemsRV)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val topAppBar: MaterialToolbar = this.findViewById(R.id.topAppBar)
        if(storeName != "null"){
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val today = LocalDateTime.now().format(formatter)
            val allLists = app.listOfgrocerylists.getAllLists()
            app.currentList = GroceryList(storeName, today)
            for(l in allLists){
                for(i in l.items){
                    if(i.store == storeName){
                        app.currentList.addItem(i)
                    }
                }
            }
        }
        topAppBar.title = app.currentList.listName
        recyclerView.adapter = myAdapter
        singleListTitleTV.text = app.currentList.listName
    }


    // Function to manually map backend data to Item class

    fun backOnClick(view: View) {
        val intent = Intent(this, ListsActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun addOnClick(view: View){
        val intent = Intent(this, AddEditItemActivity::class.java)
        app.currentItem = Item("", "", "")
        app.currentList.addItem(app.currentItem)
        startActivity(intent)
    }
}