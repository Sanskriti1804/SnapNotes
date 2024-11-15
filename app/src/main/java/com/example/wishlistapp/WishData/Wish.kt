package com.example.wishlistapp.WishData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish-table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "wish-title")
    val title: String="",
    @ColumnInfo(name = "wish-desc")
    val description: String="",
    @ColumnInfo(name = "wish-tag")
    val tag: List<String> = listOf() ,
    @ColumnInfo(name = "is-fav", defaultValue = "fav")
    val isFav : String =""
)
