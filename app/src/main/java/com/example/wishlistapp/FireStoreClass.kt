package com.example.wishlistapp

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FireStoreClass {

    private val mFireStoreClass = FirebaseFirestore.getInstance()

    fun registerUser(userInfo : User,context: Context,  onSuccess : () -> Unit){
        mFireStoreClass.collection(Constants.USERS)
            .document(getCurrentUserID())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                Toast.makeText(context, "You have registered successfuly", Toast.LENGTH_LONG).show()
                onSuccess()
            }

    }
    fun loginUser(context: Context, onSuccess: (User?) -> Unit){
        mFireStoreClass.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                    val document = task.result
                    val loggedInUser = document.toObject(User::class.java)
                    onSuccess(loggedInUser)
                }
            }
    }

    fun getCurrentUserID() : String{
        var currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null){
            currentUserID = currentUser.uid
        }
        return currentUserID
    }
}
