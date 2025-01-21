package com.prvavaja.grocerease

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class AddEditItemActivity : AppCompatActivity() {
    var stores = arrayOf<String?>(
        "None",
        "Mercator Market Pionirska Maribor",
        "Poslovni sistem Mercator d.d.",
        "Mercator Tržaška cesta",
        "Mercator Center",
        "Mercator Puhova ulica",

        "Lidl Koroška cesta",
        "Lidl Titova cesta",
        "Lidl Industrijska ulica",
        "Lidl Ulica I. Internacionale",
        "Lidl Tržaška cesta",
        "Lidl Ulica Veljka Vlahoviča",
        "Lidl Ptujska cesta",
        "Lidl Slivniška cesta",

        "Hofer Vodnikov trg",
        "Hofer Linhartova Ulica",
        "Hofer Slovenija",
        "Hofer Koroška cesta",
        "Hofer Ulica Veljka Vlahovića",
        "Hofer Šentiljska cesta",
        "Hofer Cesta proletarskih brigad",
        "Hofer Ptujska cesta",
        "Hofer Lenart",

        "Supermarket Spar Trg Svobode",
        "Supermarket Spar Žolgarjeva ulica",
        "InterSpar Pobreška cesta",
        "Hipermarket Spar Ulica Veljka Vlahoviča",
        "Restavracije InterSpar Pobreška cesta",
        "Supermarket Spar Prvomajska ulica",
        "Spar C. prolet. brigad",
        "Supermarket Spar Ptujska cesta"
    )
    lateinit var app: MyApplication
    lateinit var myAdapter: MyAdapterLists
    lateinit var serialization: Serialization // Declare it globally

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_item)

        app = application as MyApplication
        myAdapter = MyAdapterLists(app)
        serialization = Serialization(this)
        val appBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        val itemNameET = findViewById<TextView>(R.id.itemNameET)
        val amountET = findViewById<TextView>(R.id.amountET)
        val noteET = findViewById<TextView>(R.id.noteET)
        val storeDD = findViewById<Spinner>(R.id.storeDD)


        if(app.currentItem.itemName == ""){
            appBar.title = "Add new item"
        }
        else{
            appBar.title = app.currentItem.itemName
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
        var position = 0;
        for(i in 0..(stores.size-1)){
            if(stores[i] == app.currentItem.store){
                position = i
                break
            }
        }
        storeDD.setSelection(position)
    }

//TEST
    fun deleteOnClick(view: View) {
        app.currentList.removeItem(app.currentItem.uuid)
        val intent = Intent(this, SingleListActivity::class.java)
        serialization.updateInfo(app.currentList.uuid,app.currentList)
        startActivity(intent)
        finish()
    }

    fun saveOnClick(view: View){
        app.currentItem.itemName = findViewById<TextView>(R.id.itemNameET).text.toString()
        app.currentItem.amount = findViewById<TextView>(R.id.amountET).text.toString()
        app.currentItem.note = findViewById<TextView>(R.id.noteET).text.toString()
        app.currentItem.store = findViewById<Spinner>(R.id.storeDD).selectedItem.toString()

        serialization.updateInfo(app.currentList.uuid,app.currentList)
        val intent = Intent(this, SingleListActivity::class.java)
        startActivity(intent)
        finish()
    }

}