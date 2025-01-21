package com.prvavaja.grocerease

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
data class ApiResponseCompareItems(
    val store: String,
    val total: Double,
    val items: MutableList<BackendItem>
)
data class BackendItem(
    val _id: String,
    val title: String,
    val price: Double,
    val imageURL: String,
    val discount: Double,
    val category: String,
    val store: String,
    val added: String,
    val updated: String,
    val __v: Int,
    val oldPrice: Double? = 0.0,
    val amount: Int? = 0,
    val newPrice: Double? = 0.0,
)