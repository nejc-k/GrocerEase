package com.prvavaja.grocerease

import java.util.UUID
//lista ki hrani vse nakupovalne liste, se ustvari v Myaplication ko se aplikacija zazene in znotraj se hranijo vse deserializirane
//informacije, namenjen za uporabo pri recyclerview
class ListOfGroceryLists {

    private val lists = mutableListOf<GroceryList>()
    fun addList(list: GroceryList) {//add
        lists.add(list)
    }

    fun getList(uuid: UUID): GroceryList? {//get a specific list from grocery lists
        return lists.find { it.uuid == uuid }
    }

    fun updateList(updatedList: GroceryList) {//update
        val index = lists.indexOfFirst { it.uuid == updatedList.uuid }
        if (index != -1) {
            lists[index] = updatedList
        }
    }
    fun removeList(list: GroceryList) {//remove
        lists.remove(list)
    }
    fun getAllLists(): List<GroceryList> {//get all
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