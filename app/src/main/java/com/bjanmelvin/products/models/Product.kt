package com.bjanmelvin.products.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity(tableName = "products")
data class Product(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("discountPercentage")
    val discountPercentage: Double,

    @SerializedName("rating")
    val rating: Double,

    @SerializedName("stock")
    val stock: Int,

    @SerializedName("brand")
    val brand: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("thumbnail")
    val thumbnail: String,

    @SerializedName("images")
    val images: List<String>
) {
    class ImagesTypeConverter {
        @TypeConverter
        fun fromString(value: String): List<String> {
            val listType = object : TypeToken<List<String>>() {}.type
            return Gson().fromJson(value, listType)
        }

        @TypeConverter
        fun toString(value: List<String>): String {
            return Gson().toJson(value)
        }
    }
}