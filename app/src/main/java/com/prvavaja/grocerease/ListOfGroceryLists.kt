package com.prvavaja.grocerease

import java.util.UUID

class ListOfGroceryLists {

    private val lists = mutableListOf<GroceryList>()
    fun addList(list: GroceryList) {
        lists.add(list)
    }

    fun getList(uuid: UUID): GroceryList? {
        return lists.find { it.uuid == uuid }
    }

    fun updateList(updatedList: GroceryList) {
        val index = lists.indexOfFirst { it.uuid == updatedList.uuid }
        if (index != -1) {
            lists[index] = updatedList
        }
    }
    fun removeList(list: GroceryList) {
        lists.remove(list)
    }
    fun getAllLists(): List<GroceryList> {
        val sortedList = lists
        for (list in sortedList) {
            println(list.toString())
        }
        return sortedList
    }
    fun size(): Int {
        return lists.size
    }
    fun getLastList(): GroceryList {
        return lists.last()
    }
}