package com.example.wishlistapp

import android.app.Application

//extending Application( wishlistapp becomes the entry point of app lifecycle)
class WishListApp:Application() {
    //ensures onCreeate is called when the app starts up
    override fun onCreate() {
        //initial setup of application - libraries, config,etc
        super.onCreate()
        //initialize or provide dependencies using a graph class
        Graph.provide(this)
    }
}