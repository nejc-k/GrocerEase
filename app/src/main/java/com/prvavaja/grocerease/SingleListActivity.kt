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

class SingleListActivity : AppCompatActivity() {
    lateinit var app: MyApplication
    lateinit var myAdapter: MyAdapterItems
    lateinit var storeName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        storeName = intent.getStringExtra("STORE_NAME").toString()
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
    }

    fun backOnClick(view: View) {
        if(storeName != "null"){
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
        else{
            val intent = Intent(this, ListsActivity::class.java)
            startActivity(intent)
        }
        finish()
    }

    fun addOnClick(view: View){
        val intent = Intent(this, AddEditItemActivity::class.java)
        app.currentItem = Item("", "", "")
        app.currentList.addItem(app.currentItem)
        startActivity(intent)
    }
}