package com.prvavaja.grocerease

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.util.UUID
import android.content.Context

class Serialization(private val context: Context) {
    val FILE_JSON = "grocereaseinfo.json"

    // Shranjevanje podatkov v datoteko

    fun saveInfo(info: List<GroceryList>) {
        val jsonString = Json.encodeToString(info)
        //File(DATOTEKA_JSON).writeText(jsonString)
        //val file = File(application.filesDir, DATOTEKA_JSON)
        //file.writeText(jsonString)
        File(context.filesDir, FILE_JSON).writeText(jsonString)
    }

    // Branje podatkov iz datoteke
    fun readInfo(): List<GroceryList> {
        return try {
            //val jsonString = File(DATOTEKA_JSON).readText()
            val file = File(context.filesDir, FILE_JSON)
            val jsonString = file.readText()
            Json.decodeFromString(jsonString)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Dodajanje novega sadja
    fun addInfo(newList: GroceryList) {
        val curentInfo = readInfo().toMutableList()
        curentInfo.add(newList)
        saveInfo(curentInfo)
    }

    // Posodabljanje podatkov za določeno sadje
    fun updateInfo(uuid: UUID, updatedList: GroceryList) {
        val curentInfo = readInfo().toMutableList()
        val index = curentInfo.indexOfFirst { it.uuid == uuid }

        if (index != -1) {
            curentInfo[index] = updatedList
            saveInfo(curentInfo)
        }
    }

    // Brisanje sadja
    fun delete(uuid: UUID) {
        val curentInfo = readInfo().toMutableList()
        val newInfo = curentInfo.filter { it.uuid != uuid }
        saveInfo(newInfo)
    }

    fun deleteAllInfo() {
        File(context.filesDir, FILE_JSON).delete()
    }
}