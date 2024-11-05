package com.example.wishlistapp.WishData

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromStringToList(value : String) : List<String>{
        return value.split(",")     // Converts a comma-separated string to a list
    }

    @TypeConverter
    fun fromListToString(list: List<String>) : String{
        return list.joinToString(",")   // Converts a list to a comma-separated string
    }
}