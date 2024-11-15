package com.example.wishlistapp.WishData

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//idntifies class as a dao for room - it contains methods to interact w  db
@Dao
abstract class WishDao {

    //what should happen if an element of same id is being passed
    //the insert operation should be ignored
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    //suspend - allowing it to called from a coroutine for asynchronous operation
    //wishEntity is the parameter name //wish - data class - id, title and desc
    abstract suspend fun addAWish(wishEntity: Wish)

    //Flow - to handle asynchronus fetching (stream of received values )
    @Query("Select * from `wish-table`")
    abstract fun getAllWishes(): Flow<List<Wish>>

    @Update
    abstract suspend fun updateAWish(wishEntity: Wish)
    //suspend- asynchronus functioning
    @Delete
    abstract suspend fun deleteAWish(wishEntity: Wish)

    @Query("Select * from `wish-table` where id = :id")
    abstract fun getAWishById(id: Long): Flow<Wish>

}