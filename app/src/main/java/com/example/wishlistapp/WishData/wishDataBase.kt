package com.example.wishlistapp.WishData

import android.content.Context
import android.util.Log
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

//@database - marks the class as a database(room db)
//sets up the room library which is an abstraction layer over sqlite to simplify dbms manganegement and compile time checks for sql queries
@Database(
    //specifies the list of entity classes included in the db - wish entity is a part of the db
    //::class - to obtain kclass instance - provides metadata about the class - to get a kclass of wish
    //it means the room will treat the "wish" class as a tabke in the db
    entities = [Wish :: class],
    //version of db - when making a change to the schema (adding new table, column, etc) version no is incremented
    version = 2,
    //room will not export the schema - to Jdson file - used in some git shit we do not caree
      exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)

//room db for wishlist appn
@TypeConverters(Converters::class)  // Register the TypeConverters
abstract class wishDataBase : RoomDatabase() {
    //it provides necessary methods to interact with wish entity
    abstract fun wishDao() : WishDao

    //singleton obj - associated with classs - allows to access members
    //without creating an instance of the class
    companion object {
//        private const val TAG = "wishDataBase"

        //ensures visibility of change - makes sure all threads see
        // the most recent value of the variable
        @Volatile
        //nullable var - holds the instance of the db
        private var INSTANCE : wishDataBase? = null

        //providing context to the method to access the db instance/ db
        fun getDatabase(context: Context): wishDataBase {
            //way to check and create db instance safely(to prevent mul. creations)
            return INSTANCE ?: synchronized(this) {
                //initializes room db
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    wishDataBase::class.java,       //class type of the room db
                    "wish_database"             //name given to the db
                )
                    .build()
                //assigning the newlu created db to the var
                INSTANCE = instance
                instance//instance of db - that gets returned to the user
            }
        }
    }
}

