package com.prvavaja.grocerease

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
        recyclerView.adapter = myAdapter
        val singleListTitleTV: TextView = this.findViewById(R.id.singleListTitleTV)
        singleListTitleTV.text = app.currentList.listName
    }

    fun backOnClick(view: View) {
        finish()
    }
}