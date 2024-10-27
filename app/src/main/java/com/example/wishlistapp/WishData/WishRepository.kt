package com.example.wishlistapp.WishData

import kotlinx.coroutines.flow.Flow

//wishrep - acts as an intermediary bw the data source(dao) and the rest of appn
//initializing repo w WshDao instance - to provide access to db operations
//priv - wishdao is initialized privately to encapsulate the datat and access logic and ensure that it is only accessed through the repo
class WishRepository(private val wishDao: WishDao){

    suspend fun addAWish(wish: Wish){
        wishDao.addAWish(wish)
    }

    fun getWishes() : Flow<List<Wish>> = wishDao.getAllWishes()

    fun getAWishById(id : Long) : Flow<Wish>{
        return wishDao.getAWishById(id)
    }

    suspend fun updateAWish(wish: Wish){
        wishDao.updateAWish(wish)
    }

    suspend fun deleteAWish(wish: Wish){
        wishDao.deleteAWish(wish)
    }

//    fun getFavWishes() : Flow<List<Wish>> = wishDao.getFavWish()
}

//dao - sheleves in lib (adding, updating and removing a book)
//repo - librarian(handling data access req, dao metods)