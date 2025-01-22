package com.prvavaja.grocerease

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

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
        topAppBar.title = app.currentList.listName
        recyclerView.adapter = myAdapter
    }


    // Function to manually map backend data to Item class

    fun backOnClick(view: View) {
        val intent = Intent(this, ListsActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun compareItemsOnClick(view:View) {
        val intent = Intent(this, CompareList::class.java)
        startActivity(intent)
    }

    fun addOnClick(view: View){
        val intent = Intent(this, AddEditItemActivity::class.java)
        app.currentItem = Item("", "", "")
        app.currentList.addItem(app.currentItem)
        startActivity(intent)
    }
}