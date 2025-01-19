package com.prvavaja.grocerease

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

data class BackendItem(
    val _id: Id,
    val title: String,
    val price: Double,
    val imageURL: String,
    val discount: Double,
    val category: String,
    val store: String,
    val added: DateInfo,
    val updated: DateInfo,
    val __v: Int
)

@Serializable
data class Id(
    @SerialName("\$oid") val oid: String
)

@Serializable
data class DateInfo(
    @SerialName("\$date") val date: String
)