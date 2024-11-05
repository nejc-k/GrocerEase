package com.prvavaja.grocerease

import android.app.Application
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MyApplication:  Application() {
    var listOfgrocerylists=ListOfGroceryLists()//tukaj je list vseh nakupovalnih seznamov kot globalna spremenljivka
    lateinit var currentList : GroceryList
    override fun onCreate() {
        //test za testiranje izpisovanja listov
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val today = LocalDateTime.now().format(formatter)
        for(i in 1..10){
            listOfgrocerylists.addList(GroceryList("List ${i}", today))
            listOfgrocerylists.getLastList().addItem(Item("Jabolko","","100"))
            listOfgrocerylists.getLastList().addItem(Item("Banana","","40"))
            listOfgrocerylists.getLastList().addItem(Item("hruska","","10"))
        }
        super.onCreate()
    }
}