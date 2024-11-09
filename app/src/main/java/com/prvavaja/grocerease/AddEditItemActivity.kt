package com.prvavaja.grocerease

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class AddEditItemActivity : AppCompatActivity() {
    var stores = arrayOf<String?>("None", "Supernova Tabor", "Europark",
        "Mercator center Tabor")
    lateinit var app: MyApplication
    lateinit var myAdapter: MyAdapterLists

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_item)

        app = application as MyApplication
        myAdapter = MyAdapterLists(app)

        val itemTitleTV = findViewById<TextView>(R.id.itemTitleTV)
        val itemNameET = findViewById<TextView>(R.id.itemNameET)
        val amountET = findViewById<TextView>(R.id.amountET)
        val noteET = findViewById<TextView>(R.id.noteET)
        val storeDD = findViewById<Spinner>(R.id.storeDD)


        if(app.currentItem.itemName == ""){
            itemTitleTV.text = "Add new item"
        }
        else{
            itemTitleTV.text = app.currentItem.itemName
        }
        itemNameET.text = app.currentItem.itemName
        amountET.text = app.currentItem.amount
        noteET.text = app.currentItem.note

        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            R.layout.spinner_item,
            stores)
        ad.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)
        storeDD.adapter = ad
    }


    fun deleteOnClick(view: View) {
        app.currentList.removeItem(app.currentItem.uuid)
        val intent = Intent(this, SingleListActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun saveOnClick(view: View){
        app.currentItem.itemName = findViewById<TextView>(R.id.itemNameET).text.toString()
        app.currentItem.amount = findViewById<TextView>(R.id.amountET).text.toString()
        app.currentItem.note = findViewById<TextView>(R.id.noteET).text.toString()
        app.currentItem.store = findViewById<Spinner>(R.id.storeDD).selectedItem.toString()
        val intent = Intent(this, SingleListActivity::class.java)
        startActivity(intent)
        finish()
    }

}