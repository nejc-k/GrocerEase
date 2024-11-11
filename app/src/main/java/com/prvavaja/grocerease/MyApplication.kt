package com.prvavaja.grocerease

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class MyApplication : Application() {

    var listOfgrocerylists = ListOfGroceryLists()
    lateinit var currentList: GroceryList
    lateinit var currentItem: Item

    override fun onCreate() {
        super.onCreate()

        // Load and apply the saved theme preference
        val sharedPref = getSharedPreferences("AppSettings", MODE_PRIVATE)
        val isDarkMode = sharedPref.getBoolean("DARK_MODE", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        // Load grocery lists data
        val serialization = Serialization(this)
        val info = serialization.readInfo() // Reads stored data on the phone
        for (lists in info) {
            listOfgrocerylists.addList(lists)
        }
    }
}
