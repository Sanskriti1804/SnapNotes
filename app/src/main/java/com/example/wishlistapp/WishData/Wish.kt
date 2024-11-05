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

/*object Dummywish{
    val wishList = listOf(
        Wish(title = "Google watch 2",
            description = "An Android Watch",
            tag = "tag1"),
        Wish(title = "Oculus Quest 2",
            description = "A VR headset for playing games",
            tag = "tag2"),
        Wish(title = "A Sci-fi Book",
            description = "A Best seller Sci-fi action Book",
            tag = "tag3"),
        Wish(title = "Bean Bag",
            description = "A comfy bag to substitute for chair",
            tag = "tag4"),
        )
}*/