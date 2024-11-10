package com.prvavaja.grocerease

import android.app.Application
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MyApplication:  Application() {
    var listOfgrocerylists=ListOfGroceryLists()
    lateinit var currentList : GroceryList
    lateinit var currentItem : Item
    override fun onCreate() {
        super.onCreate()
        val serialization = Serialization(this)
        val info = serialization.readInfo()//prebere podatke shranjene v telefonu
        for (lists in info) {
            println(lists)
            listOfgrocerylists.addList(lists)
        }
    }
}