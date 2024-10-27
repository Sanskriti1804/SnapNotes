package com.example.wishlistapp

import android.content.Context
import androidx.room.Room
import com.example.wishlistapp.WishData.WishRepository
import com.example.wishlistapp.WishData.wishDataBase

//graph - object is used to define singleton - only one instance of the obj will be created and
// used throughout the app lifecycle(acts as global access point)
//graph is acting as an dependancy container(provide dependencies to the rest of the app)
// where it initialzes and provides instances of db nd repository needed by the app
object Graph {
    //database is of type wishdatabase
    //lateInt - to declare a non-nll var that will be initialized later // to indicate that db will be initialized after the graph obj is created but before it is used
    // when an instance needs to be initialized through dependancy injection, or a setup method that is not a constructor - the instance initialization requires some external dependenciesc or specific conditions that are not available at the time when the class is instantiated
    lateinit var database : wishDataBase

    //lazy - wishRepository will only be created when it is accessed for the first time
    //lazy delays the initialization of wishRepository until it is accessed for the first time
    //lambda fnn
    val wishRepository by lazy {
        //db.wishDao() - calls a method on dB obj to obtain an instance of wishDao
        WishRepository(wishDao = database.wishDao())
    }

    //Provide fnn - initializes the db using the room db builder // when an app starts the graph provide(context) is called to set up the db
    fun provide(context: Context){
        //dbBuilder() - method from room persistence lib - used to create a new instance of the room db
        database = Room.databaseBuilder(context, wishDataBase::class.java, "wishlist.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}