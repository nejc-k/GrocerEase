package com.prvavaja.grocerease

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListsActivity : AppCompatActivity() {

    lateinit var app: MyApplication
    lateinit var myAdapter: MyAdapterLists

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lists)

        app = application as MyApplication
        myAdapter = MyAdapterLists(app)
        val recyclerView: RecyclerView = this.findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = myAdapter
    }

    fun backOnClick(view: View) {
        finish()
    }
}