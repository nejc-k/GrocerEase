package com.prvavaja.grocerease

import kotlinx.serialization.Serializable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder

import java.util.UUID
//seraliziran razred za vsak izdelek na nakupovalnem listu
@Serializable
class Item(var itemName:String,var store:String,var amount:String, var checked:Boolean = false, var note:String="", var oldPrice:Double = 0.0, var newPrice:Double=0.0) {//konstruktor
    @Serializable(with = UUIDSerializer::class)
    var uuid: UUID = UUID.randomUUID()

    override fun equals(other: Any?): Boolean {//primerjnje po uuid dveh itemov
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val otherItem = other as Item
        return uuid == otherItem.uuid
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }

    override fun toString(): String {
        return "item, Name: "+itemName + "UUID: " + uuid +" Store: "+ store+" Amaunt: "+amount + " Note: "+ note + " Checked: "+checked.toString()
    }
    object UUIDSerializer : KSerializer<UUID> {//posebej serializacija za uuid
        override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)
        override fun deserialize(decoder: Decoder): UUID {
            return UUID.fromString(decoder.decodeString())
        }

        override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: UUID) {
            encoder.encodeString(value.toString())
        }


    }
}