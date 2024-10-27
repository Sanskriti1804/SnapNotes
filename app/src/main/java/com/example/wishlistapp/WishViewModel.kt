 package com.example.wishlistapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wishlistapp.WishData.Wish
import com.example.wishlistapp.WishData.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

//to manage ui related data and perform crud operations on the wish data using wishrepo
class WishViewModel(
    //wishvm is initialized w wish Repository
    //type - WishRepo
    //initialization- Graph.wishRepo
    //inheritance - vM()
    private val wishRepository: WishRepository = Graph.wishRepository) : ViewModel(){

    //by - to delegate the getter and setter of a prop to another object
    var wishTitleState by mutableStateOf("")
    var wishDescriptionState by mutableStateOf("")
    var wishTagState by mutableStateOf("")


    //newString - new or update string value
    fun onWishTitleChanged(newString: String){
        wishTitleState = newString
    }

    fun onWishDescriptionState(newString: String){
        wishDescriptionState = newString
    }

    fun onWishTagState(newString: String){
        wishTagState = newString
    }

    //var getAllWishes: Flow<List<Wish>> = wishRepository.getWishes() SAME THINGG but the below approach is better fora synchronouus operations and dont want to block the main UI thread

    //only it is initialized late bc it involves fetching data asynch using flow
    //will diplay a list of wishes
    lateinit var getAllWishes: Flow<List<Wish>>


    //when vm is first accessed -> init block -> fetches the list from data source  - ensuring the vm is ready to show the data to the ui
    init {
        viewModelScope.launch {
            getAllWishes = wishRepository.getWishes()
        }
    }

    var favWishes : Flow<List<Wish>> = MutableStateFlow(emptyList())

//    init {
//        viewModelScope.launch {
//            favWishes = wishRepository.getFavWishes()
//        }
//    }
    fun addWish(wish: Wish){
        //Dispatcher.IO - efers to a coroutine dispatcher in Kotlin's coroutines framework that is optimized for performing disk or network I/O operations
        viewModelScope.launch ( Dispatchers
            .IO ){
            wishRepository.addAWish(wish = wish)
        }
    }

    fun getAWishById(id : Long):Flow<Wish>{
        return wishRepository.getAWishById(id)
    }

    fun updateWish(wish: Wish){
        viewModelScope.launch (Dispatchers.IO){
            wishRepository.updateAWish(wish = wish)
        }
    }

    fun deleteWish(wish : Wish){
        viewModelScope.launch ( Dispatchers.IO ){
            wishRepository.deleteAWish(wish = wish)
        }
    }

}