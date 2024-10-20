package com.prvavaja.grocerease

import android.app.Application
class MyApplication:  Application() {
    var listOfgrocerylists=ListOfGroceryLists()//tukaj je list vseh nakupovalnih seznamov kot globalna spremenljivka
    override fun onCreate() {
        super.onCreate()
    }
}