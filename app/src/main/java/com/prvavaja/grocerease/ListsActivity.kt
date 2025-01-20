package com.prvavaja.grocerease

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ListsActivity : AppCompatActivity() {

    lateinit var app: MyApplication
    lateinit var serialization: Serialization
    lateinit var goesToStore : String
    lateinit var selectedStore : String

    private lateinit var scrollableListContainer: LinearLayout

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
        
        if(goesToStore != "null"){
          myAdapter = MyAdapterLists(app,goesToStore,selectedStore)
          val recyclerView: RecyclerView = this.findViewById(R.id.recyclerView)
          recyclerView.setHasFixedSize(true)
          recyclerView.layoutManager = LinearLayoutManager(this)
          recyclerView.adapter = myAdapter 
        }esle{
          // Find the LinearLayout container for the scrollable list
          scrollableListContainer = findViewById(R.id.scrollableListContainer)

          // Populate existing lists
          populateLists()
        }


        if(goesToStore == "null") {
            val bottomNav: BottomNavigationView
            bottomNav = findViewById(R.id.bottom_navigation)
            setupBottomNav(this, bottomNav, app.isGuest)
        }
    }

    private fun populateLists() {
        // Clear the container before adding items
        scrollableListContainer.removeAllViews()

        // Add each list as a ListItem view
        app.listOfgrocerylists.getAllLists().forEachIndexed { index, groceryList ->
            addListItem(index, groceryList.listName, groceryList.date, groceryList.items.size)
        }
    }

    fun addListOnClick(view: View) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = View.inflate(this, R.layout.add_list_dialog, null)
        val addListNameET = dialogLayout.findViewById<EditText>(R.id.addListNameET)

        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val today = LocalDateTime.now().format(formatter)

        with(builder) {
            setTitle("Name the new list:")
            setPositiveButton("OK") { _, _ ->
                val newListName = addListNameET.text.toString()
                if (newListName.isNotBlank()) {
                    app.listOfgrocerylists.addList(GroceryList(newListName, today))
                    val newIndex = app.listOfgrocerylists.size() - 1
                    val newList = app.listOfgrocerylists.getLastList()

                    // Add the new list to the container
                    addListItem(newIndex, newListName, today, 0)
                    serialization.addInfo(newList)
                }
            }
            setNegativeButton("Cancel") { dialog, _ ->
                Log.d("Lists", "Adding a list canceled!")
                dialog.dismiss()
            }
            setView(dialogLayout)
            show()
        }
    }

    fun backToMap(view:View){
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
    }
    
    private fun addListItem(index: Int, listName: String, createdDate: String, itemCount: Int) {
        val listItemView = layoutInflater.inflate(R.layout.lists_item, scrollableListContainer, false)

        val listNameTV: TextView = listItemView.findViewById(R.id.listNameTV)
        val numOfItemsTV: TextView = listItemView.findViewById(R.id.numOfItemsTV)
        val createdTV: TextView = listItemView.findViewById(R.id.createdTV)

        listNameTV.text = listName
        numOfItemsTV.text = itemCount.toString()
        createdTV.text = createdDate

        listItemView.setOnLongClickListener {
            showDeleteConfirmationDialog(this, index)
            true
        }
        listItemView.setOnClickListener {
            showGroceryList(this, index)
        }

        // Add the view to the scrollable container
        scrollableListContainer.addView(listItemView)
    }

    private fun showGroceryList(context: Context, index: Int) {
        val intent = Intent(context, SingleListActivity::class.java)
        app.currentList = app.listOfgrocerylists.getAllLists()[index]
        context.startActivity(intent)
    }

    private fun showDeleteConfirmationDialog(context: Context, position: Int) {
        if (app.listOfgrocerylists.getAllLists()[position].items.size >= 1) {
            Toast.makeText(context, "You can't delete lists that have items in it", Toast.LENGTH_SHORT).show()
            return
        }

        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Delete Item")
        alertDialogBuilder.setMessage("Are you sure you want to delete this item?")

        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            serialization.delete(app.listOfgrocerylists.getAllLists()[position].uuid)
            app.listOfgrocerylists.getAllLists().removeAt(position)

            // Refresh the scrollable list
            populateLists()
        }

        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
