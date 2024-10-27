package com.example.wishlistapp

import android.os.Parcel
import android.os.Parcelable

class User(
    val id : String = "",
    val name : String = "",
    val userName : String = "",
    val email : String = "",
    val image : String = "",
    val fcmToken : String = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest){
        writeString(id)
        writeString(name)
        writeString(userName)
        writeString(email)
        writeString(image)
        writeString(fcmToken)
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}