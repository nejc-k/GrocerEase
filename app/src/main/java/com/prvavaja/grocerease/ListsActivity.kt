package com.prvavaja.grocerease

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ListsActivity : AppCompatActivity() {

    lateinit var app: MyApplication
    lateinit var myAdapter: MyAdapterLists
    lateinit var serialization: Serialization
    lateinit var goesToStore : String
    lateinit var selectedStore : String

    override fun onCreate(savedInstanceState: Bundle?) {
        goesToStore = intent.getStringExtra("STORE_NAME").toString()
        selectedStore = intent.getStringExtra("STORE").toString()
        super.onCreate(savedInstanceState)
        if(goesToStore == "null") {
            setContentView(R.layout.activity_lists)
        }else {
            setContentView(R.layout.activity_list_to_add_to)
        }
        serialization = Serialization(this)

        app = application as MyApplication
        myAdapter = MyAdapterLists(app,goesToStore,selectedStore)
        val recyclerView: RecyclerView = this.findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = myAdapter


        if(goesToStore == "null") {
            val bottomNav: BottomNavigationView
            bottomNav = findViewById(R.id.bottom_navigation)
            setupBottomNav(this, bottomNav, app.isGuest)
        }
    }


    fun addListOnClick(view: View) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = View.inflate(this, R.layout.add_list_dialog, null)
        val addListNameET = dialogLayout.findViewById<EditText>(R.id.addListNameET)
        val recyclerView: RecyclerView = this.findViewById(R.id.recyclerView)

        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val today = LocalDateTime.now().format(formatter)

        with(builder){
            setTitle("Name the new list:")
            setPositiveButton("OK"){dialog, which ->
                app.listOfgrocerylists.addList(GroceryList(addListNameET.text.toString(), today))
                recyclerView.adapter?.notifyItemInserted(app.listOfgrocerylists.size()-1)
                serialization.addInfo(app.listOfgrocerylists.getLastList())
            }
            setNegativeButton("Cancel"){dialog, which ->
                Log.d("Lists", "Adding a list canceled!")
            }
            setView(dialogLayout)
            show()
        }
    }

    fun backToMap(view:View){
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
    }
}