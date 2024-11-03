package com.example.wishlistapp.WishData

import android.content.Context
import android.util.Log
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
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
    version = 1,
    //room will not export the schema - to Jdson file - used in some git shit we do not caree
      exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)

//room db for wishlist appn
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
//            Log.d(TAG, "Getting the database instance")
            //way to check and create db instance safely(to prevent mul. creations)
            return INSTANCE ?: synchronized(this) {
                //initializes room db
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    wishDataBase::class.java,       //class type of the room db
                    "wish_database"             //name given to the db
                )
//                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3) // Register both migrations
//                    .fallbackToDestructiveMigration()  // Add this line to reset DB if migration fails
                    .build()
                //assigning the newlu created db to the var
                INSTANCE = instance
//                Log.d(TAG, "Database instance created")
                instance//instance of db - that gets returned to the user
            }
        }

        // Define migration from version 1 to 2
//        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//            override fun migrate(db: SupportSQLiteDatabase) {
//                Log.d("Migration", "Migrating from version 2 to 3")
//                // Example migration: Adding a new column 'tag' to 'Wish' table
//                db.execSQL("ALTER TABLE `wish-table` ADD COLUMN `wish-tag` TEXT NOT NULL DEFAULT ''")
//            }
//        }
//        val MIGRATION_2_3: Migration = object : Migration(2, 3) {
//            override fun migrate(db: SupportSQLiteDatabase) {
//                Log.d("Migration", "Migrating from version 2 to 3")
//                db.execSQL("ALTER TABLE `wish-table` ADD COLUMN `is-fav` INTEGER NOT NULL DEFAULT 0")
//            }
//        }

    }
}

